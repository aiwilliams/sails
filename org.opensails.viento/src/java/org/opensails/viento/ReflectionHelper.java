package org.opensails.viento;

public class ReflectionHelper {
	public static boolean isOnlyOneArray(Class<?>[] argumentTypes) {
		return argumentTypes.length == 1 && argumentTypes[0] == Object[].class;
	}
}
