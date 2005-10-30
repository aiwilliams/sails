package org.opensails.dock;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.tester.persist.MemoryObjectPersister;

public class DockConfigurator extends BaseConfigurator {
	@Override
	protected void installObjectPersister(IConfigurableSailsApplication application, ScopedContainer container) {
		container.register(IObjectPersister.class, MemoryObjectPersister.class);
	}
}
