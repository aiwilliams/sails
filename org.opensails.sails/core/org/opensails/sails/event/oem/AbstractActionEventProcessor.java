package org.opensails.sails.event.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.Action;
import org.opensails.sails.action.oem.ActionInvocation;
import org.opensails.sails.action.oem.AdaptedParameterList;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.NoImplementationException;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;

public class AbstractActionEventProcessor<C extends IEventProcessingContext> implements IActionEventProcessor {
    protected final Map<String, Action> actions;
    protected final IAdapterResolver adapterResolver;
    protected final Class<C> processingContext;

    public AbstractActionEventProcessor(Class<C> controller, IAdapterResolver adapterResolver) {
        this.processingContext = controller;
        this.adapterResolver = adapterResolver;
        this.actions = new HashMap<String, Action>();
    }

    public C createInstance(ISailsEvent event) throws NoImplementationException {
        if (!hasImplementation()) throw new NoImplementationException(this);
        return createInstanceOrNull(event);
    }

    public Action getAction(String name) {
        Action action = actions.get(name);
        if (action == null) {
            action = new Action(name, processingContext, adapterResolver);
            actions.put(name, action);
        }
        return action;
    }

    public Class<C> getImplementation() {
        return processingContext;
    }

    public boolean hasImplementation() {
        return processingContext != null;
    }

    public IActionResult process(ExceptionEvent event) {
        C controller = createInstance(event);
        Action action = getAction(event.getActionName());
        return action.execute(new ActionInvocation(event, action, new AdaptedParameterList(event), controller));
    }

    public IActionResult process(GetEvent event) {
        return process((ISailsEvent) event);
    }

    public IActionResult process(ISailsEvent event) {
        C controller = createInstanceOrNull(event);
        Action action = getAction(event.getActionName());
        return action.execute(new ActionInvocation(event, action, event.getActionParameters(), controller));
    }

    public IActionResult process(PostEvent event) {
        return process((ISailsEvent) event);
    }

    protected C createInstance(ISailsEvent event, Class<C> contextImpl) {
        // Delegate to event container to allow dependency injection and
        // event-type-based implementations
        return event.getContainer().createEventContext(contextImpl, event);
    }

    @SuppressWarnings("unchecked")
    protected C createInstanceOrNull(ISailsEvent event) {
        if (!hasImplementation()) return null;
        C instance = createInstance(event, processingContext);
        instance.setEventContext(event, this);
        return instance;
    }
}
