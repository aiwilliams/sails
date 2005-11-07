package org.opensails.sails.adapter;

import org.opensails.rigging.ScopedContainer;

/**
 * Convenience. Already bound to a container, allows for dependence on one class
 * only.
 */
public class ContainerAdapterResolver {
	protected final ScopedContainer container;
	protected final IAdapterResolver resolver;

	public ContainerAdapterResolver(IAdapterResolver resolver, ScopedContainer container) {
		this.resolver = resolver;
		this.container = container;
	}

	public IAdapter resolve(Class<?> parameterClass) {
		return resolver.resolve(parameterClass, container);
	}
}
