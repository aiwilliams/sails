package org.opensails.sails.component;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;

public class ComponentContainer extends ScopedContainer implements IEventContextContainer {
    public ComponentContainer(IEventContextContainer requestContainer) {
        super(requestContainer, ApplicationScope.COMPONENT);
    }

    public <T extends IEventProcessingContext> T createEventContext(Class<T> key, ISailsEvent event) {
        throw new UnsupportedOperationException("A casualty of not have a clearer separation of component acting in two roles.");
    }
}
