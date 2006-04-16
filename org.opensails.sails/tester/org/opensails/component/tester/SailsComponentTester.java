package org.opensails.component.tester;

import java.io.File;

import org.opensails.sails.Sails;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.tester.browser.Browser;
import org.opensails.sails.tester.browser.SailsTestApplication;
import org.opensails.sails.tester.browser.TestGetEvent;

public class SailsComponentTester extends Browser {
	public SailsComponentTester(Class<? extends SailsConfigurator> configurator) {
		super(new SailsTestApplication(configurator));
	}

	public SailsComponentTester(Class<? extends SailsConfigurator> configurator, String contextRoot) {
		super(new SailsTestApplication(configurator, new File(contextRoot)));
	}

	/**
	 * For subclassing
	 */
	protected SailsComponentTester() {}

	public <C extends IComponentImpl> TestComponent<C> component(Class<C> componentClass) {
		TestGetEvent event = createVirtualEvent(String.format("dynamicallyGeneratedForTestingComponents/%s", Sails.componentName(componentClass)), "$instance");
		TestComponent<C> testComponent = new TestComponent<C>(this, event, componentClass);
		return testComponent;
	}
}
