package org.opensails.sails.configurator;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.RequestContainer;

public interface IObjectPersisterConfigurator {

	void configure(ApplicationContainer container);

	void configure(RequestContainer container);

}
