package org.opensails.spyglass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.spyglass.policy.SpyPolicy;

public class SpyObject<T> {

	protected final T object;
	protected final SpyClass<T> spyClass;
	protected final SpyPolicy policy;

	protected HashMap<String, InstanceProperty<T>> propertiesMap = new HashMap<String, InstanceProperty<T>>();

	/**
	 * Create a SpyObject of object using SpyPolicy.WIDEOPEN.
	 * 
	 * @param object
	 */
	@SuppressWarnings("unchecked")
	public SpyObject(T object) {
		this(object, SpyPolicy.WIDEOPEN);
	}

	@SuppressWarnings("unchecked")
	public SpyObject(T object, SpyPolicy policy) {
		this.object = object;
		this.policy = policy;
		this.spyClass = new SpyClass<T>((Class<T>) object.getClass(), policy);
	}

	/**
	 * @return the Object being spied
	 */
	public T getObject() {
		return object;
	}

	/**
	 * @param name
	 * @return a non-null property. Check isResolved() to see if available.
	 */
	public InstanceProperty<T> getProperty(String name) {
		InstanceProperty<T> property = propertiesMap.get(name);
		if (property == null) property = new InstanceProperty<T>(getSpyClass().findProperty(name), object);
		return property;
	}

	public SpyClass<T> getSpyClass() {
		return spyClass;
	}

	public Object invoke(String methodName, Object... args) {
		try {
			Method method = findMethod(object.getClass(), methodName, SpyGlass.argTypes(args));
			if (policy.canInvoke(method)) {
				method.setAccessible(true);
				return method.invoke(object, args);
			} else throw new InnaccessibleMethodException(methodName, args, policy.getMethodInvocationPolicy());
		} catch (IllegalArgumentException e) {
			throw new Crack("Could not invoke. The arguments were illegal.", e);
		} catch (IllegalAccessException e) {
			throw new Crack("Could not invoke. Know anything about access?", e);
		} catch (InvocationTargetException e) {
			throw new Crack("Could not invoke. The constructor threw and exception.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public SpyObject<?> read(Field property) {
		return new SpyObject<Object>(new SpyField(getSpyClass(), property).get(object), policy);
	}

	public SpyObject<?> read(String property) {
		Object value = getProperty(property).get();
		if (value == null) return null;
		return new SpyObject<Object>(value);
	}

	public void write(String property, Object value) {
		getProperty(property).set(value);
	}

	private Method findMethod(Class<?> clazz, String name, Class[] argTypes) {
		Method method = getSpyClass().getMethodTaking(name, argTypes);
		if (method != null) return method;
		throw new Crack(String.format("Could not find a method named %s accepting %s", name, ArrayUtils.toString(argTypes)));
	}
}
