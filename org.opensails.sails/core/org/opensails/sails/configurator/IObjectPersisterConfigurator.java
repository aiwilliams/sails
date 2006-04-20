package org.opensails.sails.configurator;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.event.ISailsEvent;

/**
 * Configures the IObjectPersister of the application.
 * 
 * @author aiwilliams
 */
public interface IObjectPersisterConfigurator {

	void configure(IConfigurableSailsApplication application, ApplicationContainer container);

	void configure(ISailsEvent event, RequestContainer container);

}
