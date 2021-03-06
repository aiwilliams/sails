package org.opensails.sails.event.oem;

import javax.servlet.http.HttpServletResponse;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.EventUrl;

public class ExceptionEvent extends AbstractEvent {
    public static final String CONTROLLER_NAME = "error";

    protected final Throwable exception;
    protected ISailsEvent originatingEvent;

    public ExceptionEvent(ISailsEvent event, Throwable t) {
        super(event.getRequest(), event.getResponse());
        this.application = event.getApplication();
        this.originatingEvent = event;
        this.exception = t;
        getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        initialize(event.getContainer());
    }

    @Override
    public void beginDispatch() {
    // not real beginning
    }

    @Override
    public void endDispatch() {
    // not real ending
    }

    public Throwable getException() {
        return exception;
    }

    public ISailsEvent getOriginatingEvent() {
        return originatingEvent;
    }

    @Override
    public IActionResult visit(IActionEventProcessor eventProcessor) {
        return eventProcessor.process(this);
    }

    @Override
    protected void containerSet() {
        container.register(this);
    }

    @Override
    protected void initialize(IScopedContainer parentContainer) {
        this.url = new EventUrl(request);
        url.setAction("exception");
        url.setController("error");

        // you could consider the originating event as the parent ;)
        container = (IEventContextContainer) parentContainer;
        containerSet();
    }
}
