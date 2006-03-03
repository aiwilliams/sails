package org.opensails.sails.adapter;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.event.ISailsEvent;

/**
 * Things in a Sails application that are interested in obtaining
 * {@link org.opensails.sails.adapter.IAdapter} instances within a particular
 * scoped container can depend on an instance of this instead of depending on
 * both an {@link org.opensails.sails.adapter.IAdapterResolver} and
 * {@link org.opensails.rigging.IScopedContainer}.
 * 
 * @see org.opensails.sails.oem.BaseConfigurator#configure(ISailsEvent,
 *      IEventContextContainer)
 * @author aiwilliams
 */
public class ContainerAdapterResolver {
    protected final IScopedContainer container;
    protected final IAdapterResolver resolver;

    public ContainerAdapterResolver(IAdapterResolver resolver, IScopedContainer container) {
        this.resolver = resolver;
        this.container = container;
    }

    public IAdapter resolve(Class<?> parameterClass) {
        return resolver.resolve(parameterClass, container);
    }
}
