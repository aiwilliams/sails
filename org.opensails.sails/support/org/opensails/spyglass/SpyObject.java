package org.opensails.spyglass;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.opensails.spyglass.policy.SpyPolicy;

public class SpyObject<T> {
	/**
	 * So you don't have to do new SpyObject<Object>(obj) everywhere.
	 * 
	 * @param <T>
	 * @param object
	 * @return a SpyObject for instance
	 */
	public static <T> SpyObject<T> create(T object) {
		return new SpyObject<T>(object);
	}

	/**
	 * So you don't have to do new SpyObject<Object>(obj) everywhere.
	 * 
	 * @param <T>
	 * @param object
	 * @param policy
	 * @return a SpyObject for instance
	 */
	public static <T> SpyObject<T> create(T object, SpyPolicy policy) {
		return new SpyObject<T>(object, policy);
	}

	protected final T object;
	protected final SpyClass<T> spyClass;
	protected final SpyPolicy policy;

	protected HashMap<String, InstanceProperty<T>> propertiesMap = new HashMap<String, InstanceProperty<T>>();

	/**
	 * Create a SpyObject of object using SpyPolicy.WIDEOPEN.
	 * 
	 * @param object
	 * @see #create(Object)
	 */
	@SuppressWarnings("unchecked")
	public SpyObject(T object) {
		this(object, SpyPolicy.WIDEOPEN);
	}

	/**
	 * @param object
	 * @param policy
	 * @see #create(Object, SpyPolicy)
	 */
	@SuppressWarnings("unchecked")
	public SpyObject(T object, SpyPolicy policy) {
		this.object = object;
		this.policy = policy;
		this.spyClass = new SpyClass<T>((Class<T>) object.getClass(), policy);
	}

	public SpyMethod<T> getMethod(String method) {
		return new SpyMethod<T>(getSpyClass(), method);
	}

	/**
	 * @return the Object being spied
	 */
	public T getObject() {
		return object;
	}

	/**
	 * @param name
	 * @return a non-null property. Check isReadable() to see if available.
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
		return new SpyMethod<T>(spyClass, methodName).invoke(object, args);
	}

	public Object read(String property) {
		return getProperty(property).get();
	}

	@SuppressWarnings("unchecked")
	public SpyObject<?> readSpy(Field property) {
		return new SpyObject<Object>(new SpyField(getSpyClass(), property).get(object), policy);
	}

	public SpyObject<?> readSpy(String property) {
		Object value = getProperty(property).get();
		if (value == null) return null;
		return new SpyObject<Object>(value);
	}

	public void write(String property, Object value) {
		getProperty(property).set(value);
	}

}
