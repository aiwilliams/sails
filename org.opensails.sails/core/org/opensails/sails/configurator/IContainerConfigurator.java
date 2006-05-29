package org.opensails.sails.configurator;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;

/**
 * Provides a hook to allow applications to configure the application scoped
 * dependency injection container.
 * 
 * @author aiwilliams
 * 
 */
public interface IContainerConfigurator {

	/**
	 * Called after the container has been installed and before any other
	 * configuration methods (except {@link IConfigurationConfigurator}). This
	 * DOES NOT disallow, nor discourage, other configurators from modifying the
	 * container.
	 * 
	 * @param application
	 * @param container
	 */
	void configure(IConfigurableSailsApplication application, ApplicationContainer container);

}
