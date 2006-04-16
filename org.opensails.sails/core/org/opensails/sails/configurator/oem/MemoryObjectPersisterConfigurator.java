package org.opensails.sails.configurator.oem;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.configurator.IObjectPersisterConfigurator;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.tester.persist.ITestObjectPersister;
import org.opensails.sails.tester.persist.MemoryObjectPersister;

public class MemoryObjectPersisterConfigurator implements IObjectPersisterConfigurator {

	public void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
		ITestObjectPersister persister = new MemoryObjectPersister();
		container.register(IObjectPersister.class, persister);
		container.register(ITestObjectPersister.class, persister);
	}

	public void configure(IConfigurableSailsApplication application, RequestContainer container) {
	// not necessary for in-memory
	}

}
