package org.opensails.spyglass;

import java.lang.reflect.Type;

public class UnresolvableProperty<T> extends SpyProperty<T> {
	public UnresolvableProperty(SpyClass<T> spyClass, String name) {
		super(spyClass, name);
	}

	@Override
	public <X extends T> Object get(X target) {
		return null;
	}

	@Override
	public Type getGenericType() {
		return null;
	}

	@Override
	public Class<?> getType() {
		return Void.TYPE;
	}

	@Override
	public <X extends T> void set(X target, Object value) {}

}
