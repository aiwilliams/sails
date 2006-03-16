package org.opensails.spyglass;

public class SpyGlassy {

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
	 * This convenience function will only work with public constructors.
	 * 
	 * @param <T>
	 * @param type
	 * @param params
	 * @return an instance of type using params
	 */
	public static <T> T instantiate(Class<T> type, Object[] params) {
		return new SpyClassy<T>(type).newInstance(params);
	}

	public static Object invoke(Object instance, String methodName, Object[] args) {
		return new SpyObject<Object>(instance).invoke(methodName, args);
	}
}
