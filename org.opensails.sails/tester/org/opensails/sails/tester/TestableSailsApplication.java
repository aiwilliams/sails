package org.opensails.sails.tester;

import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.PostEvent;
import org.opensails.sails.oem.SailsApplication;

@SuppressWarnings("serial")
public class TestableSailsApplication extends SailsApplication {
    public void configure(SailsTesterConfigurator configurator) {
        configureAndStart(configurator);
    }

    public Page get(GetEvent event) {
        dispatcher.dispatch(event);
        return new Page(event);
    }

    @Override
    public TestApplicationContainer getContainer() {
        return (TestApplicationContainer) super.getContainer();
    }

    public Page post(PostEvent event) {
        dispatcher.dispatch(event);
        return new Page(event);
    }
}
