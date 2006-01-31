package org.opensails.rigging;

public interface ComponentResolver {
	Object instance();

	/**
	 * @return whether the component is already instantiated
	 */
	boolean isInstantiated();

	/**
	 * @param container with which clone should be registered
	 * @return a new instance that behaves like this, but bound to provided
	 *         container
	 */
	ComponentResolver cloneFor(SimpleContainer container);

	/**
	 * @return the concrete type that will be returned by instance()
	 */
	Class<?> type();
}
