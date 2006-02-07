package org.opensails.functional;

import java.io.File;

import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.tester.SailsTester;

public class SailsFunctionalTester extends SailsTester {
	public SailsFunctionalTester() {
		initialize(FunctionalTestConfigurator.class, new File("test"));
	}

	public SailsFunctionalTester(Class<? extends IControllerImpl> controllerClass) {
		this();
		setWorkingContext(controllerClass);
	}
}
