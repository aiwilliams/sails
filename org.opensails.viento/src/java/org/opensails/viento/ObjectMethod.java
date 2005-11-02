package org.opensails.viento;

import java.lang.reflect.Method;

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