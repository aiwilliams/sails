package org.opensails.sails.controller;


/**
 * Used by a Sails application to resolve IController instances. When an
 * application is configured, it must have at least one of these.
 * 
 * @see org.opensails.sails.ISailsApplicationConfigurator
 * @see org.opensails.sails.oem.BaseConfigurator
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IControllerResolver {
	IController resolve(String controllerIdentifier);
}
