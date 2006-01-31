package org.opensails.sails.tester.oem;

import org.opensails.sails.*;
import org.opensails.sails.action.*;
import org.opensails.sails.event.*;
import org.opensails.sails.event.oem.*;
import org.opensails.sails.oem.*;

public class TestingDispatcher extends Dispatcher {
	public TestingDispatcher(ISailsApplication application, ISailsEventConfigurator configurator, IActionEventProcessorResolver eventProcessorResolver, IActionResultProcessorResolver resultProcessorResolver) {
		super(application, configurator, eventProcessorResolver, resultProcessorResolver);
	}

	public void doEndDispatch(ILifecycleEvent event) {
		super.endDispatch(event);
	}

	@Override
	protected void endDispatch(ILifecycleEvent event) {
	// delayed until forced
	}
}
