package org.opensails.sails.configurator.oem;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.configurator.ICacheConfigurator;
import org.opensails.sails.oem.MemoryFragmentStore;
import org.opensails.sails.template.IFragmentStore;

public class DefaultCacheConfigurator implements ICacheConfigurator {

	public void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
		container.register(IFragmentStore.class, MemoryFragmentStore.class);
	}

}
