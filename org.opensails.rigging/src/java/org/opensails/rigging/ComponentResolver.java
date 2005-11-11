package org.opensails.rigging;

public interface ComponentResolver {
	Object instance();
	boolean isInstantiated();
}
