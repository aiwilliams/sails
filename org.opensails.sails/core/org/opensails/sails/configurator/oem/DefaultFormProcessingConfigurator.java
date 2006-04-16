package org.opensails.sails.configurator.oem;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.configurator.IFormProcessingConfigurator;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.validation.IValidationEngine;
import org.opensails.sails.validation.oem.SailsValidationEngine;

public class DefaultFormProcessingConfigurator implements IFormProcessingConfigurator {

	public void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
		container.register(IValidationEngine.class, SailsValidationEngine.class);
	}

	public void configure(ISailsEvent event, RequestContainer container) {
	// do nothing
	}

}
