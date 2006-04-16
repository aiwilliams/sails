package org.opensails.sails.configurator;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.configurator.oem.DefaultFormProcessingConfigurator;
import org.opensails.sails.event.ISailsEvent;

/**
 * Configures the validation classes for the application.
 * 
 * @author aiwilliams
 * @see DefaultFormProcessingConfigurator
 */
public interface IFormProcessingConfigurator {
	void configure(IConfigurableSailsApplication application, ApplicationContainer container);

	void configure(ISailsEvent event, RequestContainer container);
}
