package org.opensails.sails.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.opensails.sails.SailsException;

/**
 * YAGNI and YARGNI, all in one class.
 * 
 * commons.lang.ClassUtils provides much of the implementation of this class. I
 * desire that Sails use this instead of ClassUtils because 1) we have a few
 * more methods it doesn't and 2) if we decide to get rid of that, the code will
 * be bound to this.
 * 
 * @author aiwilliams
 */
public class ClassHelper {
	private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

	private static Class[] argTypes(Object... args) {
		Class[] argTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++)
			argTypes[i] = args[i] == null ? null : args[i].getClass();
		return argTypes;
	}

	private static boolean argTypesExtendThese(Class[] argTypes, Class<?>[] parameterTypes) {
		if (argTypes.length != parameterTypes.length) return false;

		for (int i = 0; i < parameterTypes.length; i++)
			if (!((argTypes[i] == null && Object.class.isAssignableFrom(parameterTypes[i])) || (argTypes[i] != null && parameterTypes[i].isAssignableFrom(argTypes[i])))) return false;
		return true;
	}

	private static <T> Constructor<T> findConstructor(Class<T> clazz, Class[] argTypes) {
		Constructor[] constructors = clazz.getConstructors();
		for (Constructor<T> constructor : constructors) {
			if (argTypesExtendThese(argTypes, constructor.getParameterTypes())) return constructor;
		}
		throw new SailsException("Could not find a constructor accepting " + argTypes);
	}

	private static Method findMethod(Class<?> clazz, String name, Class[] argTypes) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(name) && argTypesExtendThese(argTypes, method.getParameterTypes())) return method;
		}
		throw new SailsException(String.format("Could not find a method named %s accepting %s", name, ArrayUtils.toString(argTypes)));
	}

	public static Object callMethod(Object instance, String methodName, Object... args) {
		try {
			return findMethod(instance.getClass(), methodName, argTypes(args)).invoke(instance, args);
		} catch (IllegalArgumentException e) {
			throw new SailsException("Could not instantiate. The arguments were illegal.", e);
		} catch (IllegalAccessException e) {
			throw new SailsException("Could not instantiate. Know anything about access?", e);
		} catch (InvocationTargetException e) {
			throw new SailsException("Could not instantiate. The constructor threw and exception.", e);
		}
	}

	/**
	 * @param clazz
	 * @param name
	 * @return an array of Methods with name, in order of least arguments to
	 *         most, or empty array if clazz is null or there are no methods
	 *         having name
	 */
	public static Method[] declaredMethodsNamed(Class<?> clazz, String name) {
		if (clazz == null) return EMPTY_METHOD_ARRAY;

		List<Method> matches = new ArrayList<Method>();
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			if (declaredMethods[i].getName().equals(name)) matches.add(declaredMethods[i]);
		}
		Collections.sort(matches, new Comparator<Method>() {
			public int compare(Method o1, Method o2) {
				return o2.getParameterTypes().length - o1.getParameterTypes().length;
			}
		});
		return matches.toArray(new Method[matches.size()]);
	}

	/**
	 * @param clazz
	 * @param name
	 * @return the Field with name or null. Searches up class heirarchy.
	 */
	public static Field fieldNamed(Class clazz, String name) {
		try {
			Class lookingAt = clazz;
			while (lookingAt != null) {
				for (Field field : lookingAt.getDeclaredFields())
					if (field.getName().equals(name)) return field;
				lookingAt = lookingAt.getSuperclass();
			}
			throw new RuntimeException(String.format("Could not find a field named %s on %s", name, clazz));
		} catch (Throwable t) {
			throw new RuntimeException(String.format("Could not access fields on %s", clazz));
		}
	}

	public static Field[] fieldsAnnotated(Class<?> clazz, Class<? extends Annotation> annotation) {
		Field[] declaredFields = clazz.getDeclaredFields();
		List<Field> annotatedFields = new ArrayList<Field>(declaredFields.length);
		for (Field field : declaredFields)
			if (field.isAnnotationPresent(annotation)) annotatedFields.add(field);
		return annotatedFields.toArray(new Field[annotatedFields.size()]);
	}

	public static Field[] fieldsNamed(Class clazz, String... names) {
		List<Field> fields = new ArrayList<Field>();
		for (String name : names)
			fields.add(fieldNamed(clazz, name));
		return fields.toArray(new Field[fields.size()]);
	}

	public static Field[] fieldsUniquelyAnnotated(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		Field[] declaredFields = clazz.getDeclaredFields();
		List<Field> annotatedFields = new ArrayList<Field>(declaredFields.length);
		Set<Annotation> annotations = new HashSet<Annotation>(declaredFields.length);
		for (Field field : declaredFields) {
			Annotation annotation = field.getAnnotation(annotationClass);
			if (field.isAnnotationPresent(annotationClass) && !annotations.contains(annotation)) {
				annotations.add(annotation);
				annotatedFields.add(field);
			}
		}
		return annotatedFields.toArray(new Field[annotatedFields.size()]);
	}

	public static String getName(Class clazz) {
		return ClassUtils.getShortClassName(clazz);
	}

	public static String getPackage(Class clazz) {
		return ClassUtils.getPackageName(clazz);
	}

	public static String getPackage(Object instance) {
		return getPackage(instance.getClass());
	}

	public static String getPackageDirectory(Class<?> clazz) {
		return getPackage(clazz).replaceAll("\\.", "/");
	}

	public static <T> T instantiate(Class<? extends T> clazz, Object... args) {
		try {
			return findConstructor(clazz, argTypes(args)).newInstance(args);
		} catch (InstantiationException e) {
			throw new SailsException(String.format("Could not instantiate a %s. No constructor matching argument types?", clazz), e);
		} catch (IllegalAccessException e) {
			throw new SailsException("Could not instantiate. Know anything about access?", e);
		} catch (IllegalArgumentException e) {
			throw new SailsException("Could not instantiate. The arguments were illegal.", e);
		} catch (SecurityException e) {
			throw new SailsException("Could not instantiate. Know anything about security?", e);
		} catch (InvocationTargetException e) {
			throw new SailsException("Could not instantiate. The constructor threw and exception.", e);
		}
	}

	/**
	 * @param clazz
	 * @param interfaze
	 * @return the interface that clazz implements where that inteface is a
	 *         descendent of interfaze
	 */
	@SuppressWarnings("unchecked")
	public static Class interfaceExtending(Class clazz, Class interfaze) {
		Class current = clazz;
		do {
			for (Class i : current.getInterfaces())
				if (interfaze.isAssignableFrom(i)) return i;
			current = current.getSuperclass();
		} while (current != Object.class);
		throw new IllegalArgumentException(String.format("The class does not implement an interface that extends %s", interfaze));
	}

	public static String lowerCamelName(Class clazz) {
		String className = getName(clazz);
		char lower = Character.toLowerCase(className.charAt(0));
		return lower + className.substring(1);
	}

	public static String lowerCamelName(Object instance) {
		return lowerCamelName(instance.getClass());
	}

	/**
	 * @param clazz
	 * @param name
	 * @return an array of public Methods with name, in order of least arguments
	 *         to most, or empty array if clazz is null or there are no methods
	 *         having name
	 */
	public static Method[] methodsNamedInHeirarchy(Class<?> clazz, String name) {
		if (clazz == null) return EMPTY_METHOD_ARRAY;

		List<Method> matches = new ArrayList<Method>();
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(name)) matches.add(methods[i]);
		}
		Collections.sort(matches, new Comparator<Method>() {
			public int compare(Method o1, Method o2) {
				return o2.getParameterTypes().length - o1.getParameterTypes().length;
			}
		});
		return matches.toArray(new Method[matches.size()]);
	}

	public static String upperCamel(String string) {
		char upper = Character.toUpperCase(string.charAt(0));
		return upper + string.substring(1);
	}
}
