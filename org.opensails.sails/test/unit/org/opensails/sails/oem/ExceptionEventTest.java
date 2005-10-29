package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.sails.ISailsEvent;

public class ExceptionEventTest extends TestCase {
    public void testInitialize() throws Exception {
        ISailsEvent originatingEvent = SailsEventFixture.actionGet("controller", "action");
        ExceptionEvent event = new ExceptionEvent(originatingEvent, new RuntimeException());
        assertEquals("error", event.getControllerName());
        assertEquals("exception", event.getActionName());
        assertNotNull(event.getApplication());
        assertSame(originatingEvent.getApplication(), event.getApplication());
    }

    public void testSetContainer() {
        ExceptionEvent event = SailsEventFixture.actionException("controller", "action");
        assertSame(event.getOriginatingEvent(), event.getContainer().instance(ISailsEvent.class));
        assertSame(event.getOriginatingEvent(), event.getContainer().instance(event.getOriginatingEvent().getClass()));
        assertSame(event, event.getContainer().instance(ExceptionEvent.class));
    }
}
