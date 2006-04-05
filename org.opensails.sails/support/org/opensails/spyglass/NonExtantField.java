package org.opensails.spyglass;

import java.lang.reflect.Type;

public class NonExtantField<T> extends SpyField<T> {

	protected final String name;

	protected NonExtantField(SpyClass<T> spyClass, String name) {
		super(spyClass, null);
		this.name = name;
	}

	@Override
	public boolean isAccessible() {
		return false;
	}

	@Override
	public Class<?> getType() {
		return null;
	}

	@Override
	public Type getGenericType() {
		return null;
	}
	
	@Override
	public <X extends T> Object get(X target) {
		return null;
	}
}
