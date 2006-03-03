package org.opensails.sails;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;

/**
 * The container of the
 * {@link org.opensails.sails.event.IEventProcessingContext}.
 */
public interface IEventContextContainer extends IScopedContainer {
    <T extends IEventProcessingContext> T createEventContext(Class<T> key, ISailsEvent event);
}
