package org.opensails.component.tester;

import org.opensails.sails.Sails;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.tester.SailsTester;
import org.opensails.sails.tester.TestGetEvent;

public class SailsComponentTester extends SailsTester {
	public SailsComponentTester(Class<? extends BaseConfigurator> configurator) {
		super(configurator);
	}

	public <C extends IComponentImpl> TestComponent<C> component(Class<C> componentClass) {
		TestGetEvent event = createVirtualEvent(String.format("dynamicallyGeneratedForTestingComponents/%s", Sails.componentName(componentClass)), "$instance");
		TestComponent<C> testComponent = new TestComponent<C>(this, event, componentClass);
		return testComponent;
	}
}
