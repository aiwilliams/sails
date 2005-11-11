package org.opensails.rigging;

public interface ComponentResolver {
	Object instance();
	/**
	 * @return whether the component is already instantiated
	 */
	boolean isInstantiated();
	/**
	 * @return the concrete type that will be returned by instance()
	 */
	Class<?> type();
}
