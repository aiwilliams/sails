package org.opensails.spyglass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.lang.ArrayUtils;

public class SpyMethod<T> {
	private final SpyClass<T> spyClass;
	private final String name;

	public SpyMethod(SpyClass<T> spyClass, String name) {
		this.spyClass = spyClass;
		this.name = name;
	}

	public boolean exists(Class... argTypes) {
		return getMethodTaking(argTypes) != null;
	}

	private Method findMethod(Class[] argTypes) {
		Method method = getMethodTaking(argTypes);
		if (method != null) return method;
		throw new Crack(String.format("Could not find a method named %s accepting %s", name, ArrayUtils.toString(argTypes)));
	}

	public Method getMethodTaking(Class[] argTypes) {
		Class nextClass = spyClass.getType();
		while (nextClass != null) {
			for (Method method : nextClass.getDeclaredMethods())
				if (method.getName().equals(name) && SpyGlass.argTypesExtendThese(argTypes, method.getParameterTypes())) return method;
			nextClass = nextClass.getSuperclass();
		}
		return null;
	}

	public Object invoke(T object, Object... args) {
		try {
			Method method = findMethod(SpyGlass.argTypes(args));
			if (spyClass.getPolicy().canInvoke(method)) {
				method.setAccessible(true);
				return method.invoke(object, args);
			} else throw new InnaccessibleMethodException(name, args, spyClass.getPolicy().getMethodInvocationPolicy());
		} catch (IllegalArgumentException e) {
			throw new Crack("Could not invoke. The arguments were illegal.", e);
		} catch (IllegalAccessException e) {
			throw new Crack("Could not invoke. Know anything about access?", e);
		} catch (InvocationTargetException e) {
			throw new Crack("Could not invoke. The constructor threw and exception.", e);
		}
	}

	public Type getGenericType(Class... argTypes) {
		return getMethodTaking(argTypes).getGenericReturnType();
	}

	public Class<?> getType(Class... argTypes) {
		return getMethodTaking(argTypes).getReturnType();
	}

}
