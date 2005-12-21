package org.opensails.functional;

import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.tester.SailsTester;

public class SailsFunctionalTester extends SailsTester {
	public SailsFunctionalTester() {
		super(FunctionalTestConfigurator.class);
	}

	public SailsFunctionalTester(Class<? extends IEventProcessingContext> contextClass) {
		this();
		setWorkingController(contextClass);
	}
}
