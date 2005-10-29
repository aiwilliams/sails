package org.opensails.sails.tester.oem;

import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.oem.ILifecycleEvent;

public class LazyActionResultProcessor implements IActionResultProcessor {
    protected final IActionResultProcessor delegate;
    protected TestingDispatcher dispatcher;
    protected boolean processed;
    protected IActionResult result;

    public LazyActionResultProcessor(IActionResultProcessor delegate) {
        this.delegate = delegate;
    }

    @SuppressWarnings("unchecked")
    public void doProcess() {
        if (!processed) {
            processed = true;
            delegate.process(result);
            dispatcher.doEndDispatch((ILifecycleEvent) result.getEvent());
        }
    }

    public void process(IActionResult result) {
        // expose this for access in other parts of the tester framework
        result.getContainer().register(LazyActionResultProcessor.class, this);
        this.result = result;
        this.dispatcher = (TestingDispatcher) result.getContainer().instance(Dispatcher.class);
    }
}
