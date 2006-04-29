package org.opensails.viento;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ObjectMethod implements CallableMethod {
	private final Method method;

	public ObjectMethod(Method method) {
		this.method = method;
	}

	@SuppressWarnings("unchecked")
	public Object call(Object target, Object[] args) {
		try {
			for (int i = 0; i < args.length; i++)
				if (args[i] instanceof UnresolvableObject) args[i] = null;
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (int i = 0; i < parameterTypes.length; i++) {
				if (parameterTypes[i].isEnum() && args[i] instanceof String)
					args[i] = Enum.valueOf((Class<? extends Enum>)parameterTypes[i], (String) args[i]);
			}
			if (ReflectionHelper.isOnlyOneArray(parameterTypes)) args = new Object[] { args };
			// Anonymous inner class method
			if (Modifier.isPublic(method.getModifiers())) method.setAccessible(true);
			Object result = method.invoke(target, args);
			if (method.getReturnType() == Void.TYPE) return "";
			if (result == null && method.isAnnotationPresent(RenderIfNull.class)) return method.getAnnotation(RenderIfNull.class).value();
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}