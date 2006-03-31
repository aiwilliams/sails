package org.opensails.sails.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opensails.sails.SailsException;
import org.opensails.spyglass.SpyGlass;

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

	/**
	 * @deprecated
	 * @see SpyGlass#invoke(Object, String, Object[])
	 * 
	 * @param instance
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static Object callMethod(Object instance, String methodName, Object... args) {
		return SpyGlass.invoke(instance, methodName, args);
	}

	public static Field[] declaredFieldsAnnotated(Class<?> clazz, Class<? extends Annotation> annotation) {
		Field[] declaredFields = clazz.getDeclaredFields();
		List<Field> annotatedFields = new ArrayList<Field>(declaredFields.length);
		for (Field field : declaredFields)
			if (field.isAnnotationPresent(annotation)) annotatedFields.add(field);
		return annotatedFields.toArray(new Field[annotatedFields.size()]);
	}

	public static Field[] declaredFieldsUniquelyAnnotated(Class<?> clazz, Class<? extends Annotation> annotationClass) {
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
			throw new RuntimeException(String.format("Could not access fields on %s", clazz), t);
		}
	}

	public static Field[] fieldsAnnotated(Class<?> clazz, Class<? extends Annotation> annotation) {
		Field[] declaredFields = clazz.getFields();
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

	/**
	 * @deprecated
	 * @see SpyGlass#getName(Class)
	 */
	public static String getName(Class clazz) {
		return SpyGlass.getName(clazz);
	}

	@Deprecated
	public static String getPackage(Class clazz) {
		return SpyGlass.getPackage(clazz);
	}

	public static String getPackage(Object instance) {
		return getPackage(instance.getClass());
	}

	public static String getPackageDirectory(Class<?> clazz) {
		return getPackage(clazz).replaceAll("\\.", "/");
	}

	/**
	 * @deprecated
	 * @see SpyGlass#instantiate(Class, Object[])
	 * 
	 * @param <T>
	 * @param clazz
	 * @param args
	 * @return
	 */
	public static <T> T instantiate(Class<? extends T> clazz, Object... args) {
		return SpyGlass.instantiate(clazz, args);
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

	/**
	 * @see SpyGlass#lowerCamelName(Class)
	 */
	@Deprecated
	public static String lowerCamelName(Class clazz) {
		return SpyGlass.lowerCamelName(clazz);
	}

	@Deprecated
	public static String lowerCamelName(Object instance) {
		return lowerCamelName(instance.getClass());
	}

	public static Method[] methodsAnnotated(Class<?> clazz, Class<? extends Annotation> annotation) {
		Method[] declaredMethods = clazz.getDeclaredMethods();
		List<Method> annotatedMethods = new ArrayList<Method>(declaredMethods.length);
		for (Method method : declaredMethods)
			if (method.isAnnotationPresent(annotation)) annotatedMethods.add(method);
		return annotatedMethods.toArray(new Method[annotatedMethods.size()]);
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

	public static Object readField(Object target, Field field) {
		try {
			return field.get(target);
		} catch (Throwable t) {
			return null;
		}
	}

	public static Object readField(Object target, String name) {
		return readField(target, name, true);
	}

	@SuppressWarnings("unchecked")
	public static Object readField(Object target, String name, boolean publicOnly) {
		Field field = fieldNamed(target.getClass(), name);
		if (!publicOnly) field.setAccessible(true);
		return readField(target, field);
	}

	public static String upperCamel(String string) {
		char upper = Character.toUpperCase(string.charAt(0));
		return upper + string.substring(1);
	}

	public static void writeDeclaredField(Object target, String field, Object value) {
		try {
			Field declaredField = target.getClass().getDeclaredField(field);
			declaredField.setAccessible(true);
			SpyGlass.write(target, declaredField, value);
		} catch (Exception e) {
			throw new SailsException("Could not write field.", e);
		}
	}
}
