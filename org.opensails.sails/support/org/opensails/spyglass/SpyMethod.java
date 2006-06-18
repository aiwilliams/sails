package org.opensails.spyglass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.viento.Name;

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

	public Type getGenericType(Class... argTypes) {
		return getMethodTaking(argTypes).getGenericReturnType();
	}

	/**
	 * @param argTypes
	 * @return the Method taking argTypes exactly or super-types thereof
	 */
	public Method getMethodTaking(Class[] argTypes) {
		Class nextClass = spyClass.getType();
		while (nextClass != null) {
			for (Method method : nextClass.getDeclaredMethods())
				if (nameMatch(name, method) && SpyGlass.argTypesExtendThese(argTypes, method.getParameterTypes())) return method;
			nextClass = nextClass.getSuperclass();
		}
		return null;
	}

	public Class<?> getType(Class... argTypes) {
		return getMethodTaking(argTypes).getReturnType();
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

	// TODO Move Name annotation out of Viento into SpyGlass
	protected boolean nameMatch(String methodName, Method method) {
		if (method.getName().equals(name)) return true;
		else if (method.isAnnotationPresent(Name.class)) for (String name : method.getAnnotation(Name.class).value())
			if (name.equals(methodName)) return true;
		return false;
	}

	private Method findMethod(Class[] argTypes) {
		Method method = getMethodTaking(argTypes);
		if (method != null) return method;
		throw new Crack(String.format("Could not find a method named %s accepting %s", name, ArrayUtils.toString(argTypes)));
	}

}
