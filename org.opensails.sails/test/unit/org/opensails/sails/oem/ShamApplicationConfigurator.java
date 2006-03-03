package org.opensails.sails.oem;

import org.opensails.rigging.Startable;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;

public class ShamApplicationConfigurator extends BaseConfigurator {
	public boolean containerStarted;

	@Override
	public ApplicationContainer installContainer(IConfigurableSailsApplication application) {
		ApplicationContainer container = super.installContainer(application);
		container.register(ShamStartable.class, new ShamStartable());
		return container;
	}

	class ShamStartable implements Startable {
		public void start() {
			containerStarted = true;
		}
	}
}
