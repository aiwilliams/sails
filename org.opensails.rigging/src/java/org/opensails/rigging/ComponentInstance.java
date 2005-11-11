package org.opensails.rigging;

public class ComponentInstance implements ComponentResolver {
	protected Object instance;

	public ComponentInstance(Object instance) {
		this.instance = instance;
	}
	
	public Object instance() {
		return instance;
	}

	public boolean isInstantiated() {
		return true;
	}
}
