package org.opensails.sails.oem;

import org.apache.commons.configuration.Configuration;
import org.opensails.sails.servletapi.ShamHttpServletRequest;
import org.opensails.sails.servletapi.ShamHttpServletResponse;

public class ShamEvent extends AbstractEvent {
    public boolean beginDispatchCalled;
    public boolean endDispatchCalled;

    public ShamEvent() {
        super(SailsApplicationFixture.basic(), new ShamHttpServletRequest(), new ShamHttpServletResponse());
    }

    @Override
    public void beginDispatch() {
        beginDispatchCalled = true;
    }

    @Override
    public void endDispatch() {
        endDispatchCalled = true;
    }
}
