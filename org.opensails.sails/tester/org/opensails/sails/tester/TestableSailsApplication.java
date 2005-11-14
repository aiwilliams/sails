package org.opensails.sails.tester;

import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.PostEvent;
import org.opensails.sails.oem.SailsApplication;

public class TestableSailsApplication extends SailsApplication {
	public void configure(SailsTesterConfigurator configurator) {
		configureAndStart(configurator);
	}

	public Page get(GetEvent event) {
		dispatcher.dispatch(event);
		return new Page(event);
	}

	public Page post(PostEvent event) {
		dispatcher.dispatch(event);
		return new Page(event);
	}
}
