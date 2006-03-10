package org.opensails.sails.component;

import org.opensails.sails.event.INamespacedProcessorResolver;

/**
 * Used by a Sails application to resolve IController instances. When an
 * application is configured, it must have at least one of these.
 * 
 * @see org.opensails.sails.ISailsApplicationConfigurator
 * @see org.opensails.sails.oem.BaseConfigurator
 * 
 * @author aiwilliams
 */
public interface IComponentResolver<C extends IComponent> extends INamespacedProcessorResolver<C> {
	C resolve(String componentIdentifier);
}
