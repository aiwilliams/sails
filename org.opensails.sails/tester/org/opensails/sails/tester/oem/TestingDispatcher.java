package org.opensails.sails.tester.oem;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.event.IActionEventProcessorResolver;
import org.opensails.sails.event.oem.ILifecycleEvent;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.tester.TestApplicationContainer;

public class TestingDispatcher extends Dispatcher {
	public TestingDispatcher(ISailsApplication application, IEventConfigurator configurator, IActionEventProcessorResolver eventProcessorResolver, IActionResultProcessorResolver resultProcessorResolver) {
		super(application, configurator, eventProcessorResolver, resultProcessorResolver);
	}

	public void doEndDispatch(ILifecycleEvent event) {
		super.endDispatch(event);
	}

	@Override
	protected void beginDispatch(ILifecycleEvent event) {
		startNewlyInjectedApplicationScopedStartables();
		super.beginDispatch(event);
	}

	@Override
	protected void endDispatch(ILifecycleEvent event) {
	// delayed until forced
	}

	protected void startNewlyInjectedApplicationScopedStartables() {
		((TestApplicationContainer) application.getContainer()).startInjections();
	}
}
