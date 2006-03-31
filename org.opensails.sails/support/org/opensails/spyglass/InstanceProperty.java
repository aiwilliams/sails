package org.opensails.spyglass;

/**
 * Provides access to a property on an object through a field.
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

	public void set(Object value) {
		property.set(object, value);
	}

}
