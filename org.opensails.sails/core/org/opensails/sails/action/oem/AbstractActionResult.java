package org.opensails.sails.action.oem;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.http.HttpHeader;

public abstract class AbstractActionResult implements IActionResult {
    protected final ISailsEvent event;

    public AbstractActionResult(ISailsEvent event) {
        this.event = event;
    }

    public IEventContextContainer getContainer() {
        return event.getContainer();
    }

    public IEventProcessingContext getProcessingContext() {
        return event.getContainer().instance(IEventProcessingContext.class);
    }

    public ISailsEvent getEvent() {
        return event;
    }

    public void setHeader(HttpHeader header) {
        event.getResponse().setHeader(header.name(), header.value());
    }
}
