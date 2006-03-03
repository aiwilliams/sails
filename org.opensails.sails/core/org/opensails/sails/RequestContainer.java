package org.opensails.sails;

import org.opensails.rigging.IScopedContainer;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.util.ClassHelper;

public class RequestContainer extends ScopedContainer implements IEventContextContainer {
    public RequestContainer(IScopedContainer parent) {
        super(parent, ApplicationScope.REQUEST);
        parent.addChild(this);
    }

    public RequestContainer(IScopedContainer parent, ISailsEvent event) {
        this(parent);
        bind(event);
    }

    @SuppressWarnings("unchecked")
    public <T extends IEventProcessingContext> T createEventContext(Class<T> key, ISailsEvent event) {
        T instance = super.instance(key, key);
        register(IEventProcessingContext.class, instance);
        register(ClassHelper.interfaceExtending(instance.getClass(), IEventProcessingContext.class), instance);
        return (T) instance;
    }

    @Override
    public void dispose() {
        super.dispose();
        getParent().removeChild(this);
    }

    /**
     * Bind the event to this container
     * 
     * @param event
     */
    protected void bind(ISailsEvent event) {
        register(ISailsEvent.class, event);
        register(event);
    }
}
