package org.opensails.sails.oem;

import org.opensails.rigging.ScopedContainer;
import org.opensails.rigging.Startable;
import org.opensails.sails.IConfigurableSailsApplication;

public class ShamApplicationConfigurator extends BaseConfigurator {
    public boolean containerStarted;

    @Override
    public ScopedContainer installContainer(IConfigurableSailsApplication application) {
        ScopedContainer container = super.installContainer(application);
        container.register(ShamStartable.class, new ShamStartable());
        return container;
    }

    class ShamStartable implements Startable {
        public void start() {
            containerStarted = true;
        }
    }
}
