package org.opensails.spyglass;

import java.lang.reflect.Type;

public abstract class SpyProperty<T> {
	protected final SpyClass<T> spyClass;
	protected final String name;

	protected SpyProperty(SpyClass<T> spyClass, String propertyOnSpyClass) {
		this.spyClass = spyClass;
		this.name = propertyOnSpyClass;
	}

	public abstract <X extends T> Object get(X target);

	/**
	 * @return the generic type of this property
	 * @see SpyField
	 * @see BeanProperty
	 */
	public abstract Type getGenericType();

	public String getName() {
		return name;
	}

	public SpyClass<T> getSpyClass() {
		return spyClass;
	}

	public abstract Class<?> getType();

	public abstract <X extends T> void set(X target, Object value);
}
