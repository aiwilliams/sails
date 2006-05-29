package org.opensails.sails.configurator;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;

/**
 * Configures the application's caches.
 * 
 * @author aiwilliams
 */
public interface ICacheConfigurator {
	void configure(IConfigurableSailsApplication application, ApplicationContainer container);
}
