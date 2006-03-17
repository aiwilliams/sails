package org.opensails.spyglass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;

public class SpyObject<T> {

	private final T object;

	public SpyObject(T object) {
		this.object = object;
	}

	public Object invoke(String methodName, Object... args) {
		try {
			return findMethod(object.getClass(), methodName, SpyGlass.argTypes(args)).invoke(object, args);
		} catch (IllegalArgumentException e) {
			throw new Crack("Could not instantiate. The arguments were illegal.", e);
		} catch (IllegalAccessException e) {
			throw new Crack("Could not instantiate. Know anything about access?", e);
		} catch (InvocationTargetException e) {
			throw new Crack("Could not instantiate. The constructor threw and exception.", e);
		}
	}

	private Method findMethod(Class<?> clazz, String name, Class[] argTypes) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(name) && SpyGlass.argTypesExtendThese(argTypes, method.getParameterTypes())) return method;
		}
		throw new Crack(String.format("Could not find a method named %s accepting %s", name, ArrayUtils.toString(argTypes)));
	}

}
