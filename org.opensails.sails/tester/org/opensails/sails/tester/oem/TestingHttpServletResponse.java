package org.opensails.sails.tester.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class TestingHttpServletResponse extends ShamHttpServletResponse {
    protected ISailsEvent event;

    @Override
    public String getWrittenContent() {
        processor().doProcess();
        return super.getWrittenContent();
    }

    public void set(ISailsEvent event) {
        this.event = event;
    }

    protected LazyActionResultProcessor processor() {
        return event.getContainer().instance(LazyActionResultProcessor.class);
    }
}
