package org.opensails.sails.configurator;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.RequestContainer;


public interface IContainerConfigurator {

	void configure(ApplicationContainer applicationContainer);
	
//	applicationContainer.register(IValidationEngine.class, SailsValidationEngine.class);
//	applicationContainer.register(IElementIdGenerator.class, UnderscoreIdGenerator.class);

	void configure(RequestContainer requestContainer);

}
