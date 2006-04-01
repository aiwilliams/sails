package org.opensails.spyglass;

import java.lang.reflect.Type;

/**
 * Provides access to a property on an object through a field.
 * 
 * @param <T> the type of object this property is for
 * 
 * @author aiwilliams
 */
public class InstanceProperty<T> {
	protected final T object;
	protected final SpyProperty<T> property;

	public InstanceProperty(SpyProperty<T> property, T object) {
		this.property = property;
		this.object = object;
	}

	public Object get() {
		return property.get(object);
	}

	public Type getGenericType() {
		return property.getGenericType();
	}

	public Class<?> getType() {
		return property.getType();
	}

	public boolean isResolved() {
		return property.getClass() != UnresolvableProperty.class;
	}

	public void set(Object value) {
		property.set(object, value);
	}

}
