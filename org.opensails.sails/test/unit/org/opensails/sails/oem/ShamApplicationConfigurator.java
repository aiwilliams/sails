package org.opensails.sails.oem;

import org.opensails.rigging.Startable;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.configurator.SailsConfigurator;

public class ShamApplicationConfigurator extends SailsConfigurator {
	public boolean containerStarted;

	@Override
	public ApplicationContainer installContainer() {
		ApplicationContainer container = super.installContainer();
		container.register(ShamStartable.class, new ShamStartable());
		return container;
	}

	class ShamStartable implements Startable {
		public void start() {
			containerStarted = true;
		}
	}
}
