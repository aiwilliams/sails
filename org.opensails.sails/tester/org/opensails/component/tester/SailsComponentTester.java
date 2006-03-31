package org.opensails.component.tester;

import org.opensails.sails.Sails;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.tester.browser.Browser;
import org.opensails.sails.tester.browser.SailsTestApplication;
import org.opensails.sails.tester.browser.TestGetEvent;

public class SailsComponentTester extends Browser {
	public SailsComponentTester(Class<? extends BaseConfigurator> configurator) {
		super(new SailsTestApplication(configurator));
	}

	public <C extends IComponentImpl> TestComponent<C> component(Class<C> componentClass) {
		TestGetEvent event = createVirtualEvent(String.format("dynamicallyGeneratedForTestingComponents/%s", Sails.componentName(componentClass)), "$instance");
		TestComponent<C> testComponent = new TestComponent<C>(this, event, componentClass);
		return testComponent;
	}
}
