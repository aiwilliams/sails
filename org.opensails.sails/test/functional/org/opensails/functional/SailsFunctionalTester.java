package org.opensails.functional;

import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.tester.SailsTester;

public class SailsFunctionalTester extends SailsTester {
	public SailsFunctionalTester() {
		super(FunctionalTestConfigurator.class);
	}

	public SailsFunctionalTester(Class<? extends IControllerImpl> controllerClass) {
		this();
		setWorkingController(controllerClass);
	}
}
