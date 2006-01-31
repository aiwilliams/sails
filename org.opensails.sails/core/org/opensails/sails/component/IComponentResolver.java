package org.opensails.sails.component;

import org.opensails.sails.event.*;

/**
 * Used by a Sails application to resolve IController instances. When an
 * application is configured, it must have at least one of these.
 * 
 * @see org.opensails.sails.ISailsApplicationConfigurator
 * @see org.opensails.sails.oem.BaseConfigurator
 * 
 * @author aiwilliams
 */
public interface IComponentResolver extends INamespacedProcessorResolver<IComponent> {
	IComponent resolve(String componentIdentifier);
}
