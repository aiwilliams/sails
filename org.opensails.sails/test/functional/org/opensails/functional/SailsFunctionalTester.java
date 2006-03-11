package org.opensails.functional;

import java.io.File;

import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.tester.browser.Browser;
import org.opensails.sails.tester.browser.SailsTestApplication;

public class SailsFunctionalTester extends Browser {
	public SailsFunctionalTester() {
		SailsTestApplication application = new SailsTestApplication(FunctionalTestConfigurator.class, new File("test"));
		application.registerBrowser(this);
		initialize(application);
	}

	public SailsFunctionalTester(Class<? extends IControllerImpl> controllerClass) {
		this();
		setWorkingContext(controllerClass);
	}
}
