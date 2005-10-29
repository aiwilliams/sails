package org.opensails.sails.controller;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;

public interface IController {
    /**
     * @return the container that created this
     */
    ScopedContainer getContainer();

    /**
     * Called before the controller is invoked, but only if it is invoked
     * 
     * @param event
     */
    void set(ISailsEvent event);
}
