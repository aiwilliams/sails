package org.opensails.hibernate;

import org.opensails.hibernate.validation.HibernateValidationEngine;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.configurator.IFormProcessingConfigurator;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.validation.IValidationEngine;

public class HibernateFormProcessingConfigurator implements IFormProcessingConfigurator {

	public void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
		container.register(IValidationEngine.class, HibernateValidationEngine.class);
	}

	public void configure(ISailsEvent event, RequestContainer container) {
	// do nothing
	}

}
