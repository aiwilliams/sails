package org.opensails.sails.tester.oem;

import org.opensails.sails.IActionResultProcessorResolver;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsEventConfigurator;
import org.opensails.sails.controller.oem.IControllerResolver;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.oem.ILifecycleEvent;

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
