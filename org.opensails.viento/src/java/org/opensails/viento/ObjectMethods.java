package org.opensails.viento;

import java.lang.reflect.Method;

public class ObjectMethods {

	public CallableMethod find(TargetedMethodKey key) {
		Method method = findAppropriateMethod(key.targetClass, key.methodName, key.argClasses);
		if (method == null)
			return null;
		return new ObjectMethod(method);
	}
	
	protected Method findAppropriateMethod(Class<?> type, String methodName, Class[] args) {
		Method theMethod = null;
		Method[] methods = type.getMethods();
		for (Method method : methods)
			if (nameMatch(methodName, method) && method.getParameterTypes().length == args.length && typesMatch(method.getParameterTypes(), args, theMethod))
				theMethod = method;
		if (type.getSuperclass() != null && theMethod == null)
			return findAppropriateMethod(type.getSuperclass(), methodName, args);
		return theMethod;
	}
	
	protected boolean nameMatch(String methodName, Method method) {
		if (method.isAnnotationPresent(Name.class)) return method.getAnnotation(Name.class).value().equals(methodName);
		return method.getName().equals(methodName) || method.getName().equals(getter(methodName));
	}
	
	protected String getter(String methodName) {
		return "get" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
	}

	protected boolean typesMatch(Class<?>[] parameterTypes, Class[] args, Method theMethod) {
		for (int i = 0; i < parameterTypes.length; i++)
			if (!typesMatch(parameterTypes[i], args[i]) || (theMethod != null && parameterTypes[i].isAssignableFrom(theMethod.getParameterTypes()[i]))) return false;
		return true;
	}

	protected boolean typesMatch(Class<?> parameterType, Class arg) {
		if (arg == null)
			return !parameterType.isPrimitive();
		return parameterType.isAssignableFrom(arg) || primitiveMatch(parameterType, arg);
	}
	
	protected boolean primitiveMatch(Class<?> type, Class arg) {
		return ((type == boolean.class && arg == Boolean.class) || (type == char.class && arg == Character.class)
				|| (type == byte.class && arg == Byte.class) || (type == short.class && arg == Short.class)
				|| (type == int.class && arg == Integer.class) || (type == long.class && arg == Long.class)
				|| (type == float.class && arg == Float.class) || (type == double.class && arg == Double.class));
	}


	public class ObjectMethod implements CallableMethod {
		private final Method method;

		public ObjectMethod(Method method) {
			this.method = method;
		}

		public Object call(Object target, Object[] args) {
			try {
				Object result = method.invoke(target, args);
				if (method.getReturnType() == Void.TYPE) return "";
				return result;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}