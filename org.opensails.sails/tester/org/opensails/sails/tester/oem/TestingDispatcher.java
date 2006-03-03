package org.opensails.sails.tester.oem;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.event.IActionEventProcessorResolver;
import org.opensails.sails.event.ISailsEventConfigurator;
import org.opensails.sails.event.oem.ILifecycleEvent;
import org.opensails.sails.oem.Dispatcher;

public class TestingDispatcher extends Dispatcher {
    public TestingDispatcher(ISailsApplication application, ISailsEventConfigurator configurator, IActionEventProcessorResolver eventProcessorResolver, IActionResultProcessorResolver resultProcessorResolver) {
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

    /*
     * This works closely with the TestApplicationContainer
     */
    protected void startNewlyInjectedApplicationScopedStartables() {
        application.getContainer().start();
    }
}
