package org.opensails.spyglass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.opensails.sails.model.oem.DotPropertyPath;

public class SpyGlass {

	private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

	/**
	 * @param args
	 * @return a Class[] containing the concrete types of args
	 */
	public static Class[] argTypes(Object... args) {
		Class[] argTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++)
			argTypes[i] = args[i] == null ? null : args[i].getClass();
		return argTypes;
	}

	/**
	 * @param argTypes
	 * @param parameterTypes
	 * @return true if the arrays are equal in length and for each position the
	 *         Class in argTypes is a subclass of that in parameterTypes
	 */
	public static boolean argTypesExtendThese(Class[] argTypes, Class<?>[] parameterTypes) {
		if (argTypes.length != parameterTypes.length) return false;
		for (int i = 0; i < parameterTypes.length; i++)
			if (!((argTypes[i] == null && Object.class.isAssignableFrom(parameterTypes[i])) || (argTypes[i] != null && parameterTypes[i].isAssignableFrom(argTypes[i])))) return false;
		return true;
	}

	/**
	 * @param clazz
	 * @param name
	 * @return the declared field in class heirarchy with name or null
	 */
	@SuppressWarnings("unchecked")
	public static Field getField(Class clazz, String name) {
		return new SpyClass<Object>(clazz).getField(name);
	}

	@SuppressWarnings("unchecked")
	public static Method getMethod(Class clazz, String name) {
		return new SpyClass<Object>(clazz).getMethod(name);
	}

	public static String getName(Class<? extends Object> clazz) {
		if (clazz == null) return null;

		DotPropertyPath namePath = new DotPropertyPath(clazz.getName());
		String name = namePath.getLastProperty();
		int innerClassSeparatorIndex = name.indexOf('$');
		if (innerClassSeparatorIndex > 0) name = name.substring(innerClassSeparatorIndex + 1);

		return name;
	}

	public static String getPackageDirectory(Class<?> clazz) {
		return getPackageName(clazz).replaceAll("\\.", "/");
	}

	public static <T> String getPackageName(Class<T> clazz) {
		return new SpyClass<T>(clazz).getPackageName();
	}

	public static String getPackageName(Object instance) {
		return getPackageName(instance.getClass());
	}

	/**
	 * @param <T>
	 * @param type
	 * @return an instance of type using params
	 */
	public static <T> T instantiate(Class<T> type) {
		return new SpyClass<T>(type).newInstance();
	}

	/**
	 * @param <T>
	 * @param type
	 * @param params
	 * @return an instance of type using params
	 */
	public static <T> T instantiate(Class<T> type, Object[] params) {
		return new SpyClass<T>(type).newInstance(params);
	}

	public static Object invoke(Object instance, String methodName, Object[] args) {
		return SpyObject.create(instance).invoke(methodName, args);
	}

	public static String lowerCamelName(Class<? extends Object> clazz) {
		return lowerCamelName(getName(clazz));
	}

	public static String lowerCamelName(Object instance) {
		return lowerCamelName(instance.getClass());
	}

	public static String lowerCamelName(String name) {
		char lower = Character.toLowerCase(name.charAt(0));
		return lower + name.substring(1);
	}

	public static Method[] methodsNamedInHeirarchy(Class clazz, String name) {
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

	@SuppressWarnings("unchecked")
	public static Object read(Object instance, Field field) {
		return new SpyField<Object>(new SpyClass(field.getDeclaringClass()), field).get(instance);
	}

	public static String upperCamelName(Class<? extends Object> clazz) {
		return upperCamelName(ClassUtils.getShortClassName(clazz));
	}

	public static String upperCamelName(Object instance) {
		return upperCamelName(instance.getClass());
	}

	public static String upperCamelName(String name) {
		char upper = Character.toUpperCase(name.charAt(0));
		return upper + name.substring(1);
	}

	@SuppressWarnings("unchecked")
	public static void write(Object instance, Field field, Object value) {
		new SpyField<Object>(new SpyClass(field.getDeclaringClass()), field).set(instance, value);
	}
}
