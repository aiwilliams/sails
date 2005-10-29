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

	/**
	 * Exposes container creation for use in TestableSailsApplication. Once this
	 * has been called, subsequent calls will have no effect, thereby allowing
	 * configuration of the event container before the event is dispatched.
	 */
	@Override
	public void installContainer(ILifecycleEvent event) {
		if (event.getContainer() != null) return;
		super.installContainer(event);
	}

	@Override
	protected void endDispatch(ILifecycleEvent event) {
	// delayed until forced
	}
}
