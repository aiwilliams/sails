package org.opensails.viento;

import java.lang.reflect.Method;

public class ObjectMethod implements CallableMethod {
	private final Method method;

	public ObjectMethod(Method method) {
		this.method = method;
	}

	public Object call(Object target, Object[] args) {
		try {
			for (int i = 0; i < args.length; i++)
				if (args[i] instanceof UnresolvableObject)
					args[i] = null;
			if (ReflectionHelper.isOnlyOneArray(method.getParameterTypes()))
				args = new Object[] {args};
			Object result = method.invoke(target, args);
			if (method.getReturnType() == Void.TYPE) return "";
			if (result == null && method.isAnnotationPresent(RenderIfNull.class))
				return method.getAnnotation(RenderIfNull.class).value();
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}