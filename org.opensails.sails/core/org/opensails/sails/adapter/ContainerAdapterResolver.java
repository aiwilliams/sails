package org.opensails.sails.adapter;

import org.opensails.rigging.ScopedContainer;

/**
 * Things in a Sails application that are interested in obtaining
 * {@link org.opensails.sails.adapter.IAdapter} instances within a particular
 * scoped container can depend on an instance of this instead of depending on
 * both an {@link org.opensails.sails.adapter.IAdapterResolver} and
 * {@link org.opensails.rigging.ScopedContainer}.
 * 
 * @author Adam 'Programmer' Williams
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
