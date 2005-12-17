package org.opensails.sails.tester.oem;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.event.ISailsEventConfigurator;
import org.opensails.sails.event.oem.ILifecycleEvent;
import org.opensails.sails.oem.Dispatcher;

public class TestingDispatcher extends Dispatcher {
	public TestingDispatcher(ISailsApplication application, ISailsEventConfigurator configurator, IControllerResolver controllerResolver, IActionResultProcessorResolver processorResolver) {
		super(application, configurator, controllerResolver, processorResolver);
	}

	public void doEndDispatch(ILifecycleEvent event) {
		super.endDispatch(event);
	}

	@Override
	protected void endDispatch(ILifecycleEvent event) {
	// delayed until forced
	}
}
