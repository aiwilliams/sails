package org.opensails.spyglass;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class SpyProperty<T> {
	protected final SpyClass<T> spyClass;
	protected final String name;
	protected SpyField<T> field;
	protected SpyMethod<T> getter;
	protected SpyMethod<T> setter;

	protected SpyProperty(SpyClass<T> spyClass, String propertyOnSpyClass) {
		this.spyClass = spyClass;
		this.name = propertyOnSpyClass;
		this.field = findFieldProperty(name);
		this.getter = findGetter(name);
		this.setter = findSetter(name);
	}

	private SpyMethod<T> findSetter(String name) {
		return new SpyMethod<T>(spyClass, "set" + SpyGlass.upperCamelName(name));
	}

	private SpyMethod<T> findGetter(String name) {
		return new SpyMethod<T>(spyClass, "get" + SpyGlass.upperCamelName(name));
	}

	public <X extends T> Object get(X target) {
		if (getter.exists()) return getter.invoke(target);
		else return field.get(target);
	}

	/**
	 * Find the property as a field. Only called when not in cache. Caches
	 * accessible and inaccessible field properties.
	 * 
	 * @param name
	 * @return the property or null if not found
	 */
	private SpyField<T> findFieldProperty(String name) {
		for (Field field : spyClass.getFields()) {
			String fieldName = field.getName();
			// not the instance of containing class when anonymous innerclass
			if (fieldName.indexOf('$') == -1) {
				if (!fieldName.equals(name)) continue;
				return new SpyField<T>(spyClass, field);
			}
		}
		return new NonExtantField<T>(spyClass, name);
	}

	/**
	 * @return the generic type of this property
	 * @see SpyField
	 * @see BeanProperty
	 */
	public Type getGenericType() {
		if (getter.exists()) return getter.getGenericType();
		else return field.getGenericType();
	}

	public String getName() {
		return name;
	}

	public SpyClass<T> getSpyClass() {
		return spyClass;
	}

	public Class<?> getType() {
		if (getter.exists()) return getter.getType();
		else return field.getType();
	}

	public <X extends T> void set(X target, Object value) {
		if (setter.exists(value.getClass())) setter.invoke(target, value);
		else field.set(target, value);
	}

	public boolean isReadable() {
		return getter.exists() || field.isAccessible();
	}
}
