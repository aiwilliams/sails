package org.opensails.sails;

import org.opensails.rigging.ScopedContainer;

public interface ISailsEventConfigurator {
    /**
     * Called every time an event is dispatched
     * 
     * @param event
     * @param eventContainer convenience, event.getContainer() == eventContainer
     */
    void configure(ISailsEvent event, ScopedContainer eventContainer);

}
