package org.opensails.spyglass;

import java.lang.reflect.Field;

import org.apache.commons.lang.ClassUtils;

public class SpyGlass {

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

	public static String getName(Class clazz) {
		return ClassUtils.getShortClassName(clazz);
	}

	public static <T> String getPackage(Class<T> clazz) {
		return new SpyClass<T>(clazz).getPackageName();
	}

	public static String getPackage(Object instance) {
		return getPackage(instance.getClass());
	}

	public static String getPackageDirectory(Class<?> clazz) {
		return getPackage(clazz).replaceAll("\\.", "/");
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
		return new SpyObject<Object>(instance).invoke(methodName, args);
	}

	public static String lowerCamelName(Class clazz) {
		return lowerCamelName(ClassUtils.getShortClassName(clazz));
	}

	public static String lowerCamelName(Object instance) {
		return lowerCamelName(instance.getClass());
	}

	public static String lowerCamelName(String name) {
		char lower = Character.toLowerCase(name.charAt(0));
		return lower + name.substring(1);
	}

	public static String upperCamelName(Class clazz) {
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
