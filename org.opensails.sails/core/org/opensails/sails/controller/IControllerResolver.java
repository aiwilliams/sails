package org.opensails.sails.controller;

import org.opensails.sails.event.*;

/**
 * Used by a Sails application to resolve IController instances. When an
 * application is configured, it must have at least one of these.
 * 
 * @see org.opensails.sails.ISailsApplicationConfigurator
 * @see org.opensails.sails.oem.BaseConfigurator
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IControllerResolver extends INamespacedProcessorResolver<IController> {
	IController resolve(String controllerIdentifier);
}
