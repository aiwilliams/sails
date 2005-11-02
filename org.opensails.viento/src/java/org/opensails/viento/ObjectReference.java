package org.opensails.viento;

public class ObjectReference implements CallableMethod {
	private final Object object;

	public ObjectReference(Object object) {
		this.object = object;
	}

	public Object call(Object target, Object[] args) {
		return object;
	}
}