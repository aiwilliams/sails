package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.sails.ISailsEvent;

public class GetEventTest extends TestCase {
    public void testSetContainer() {
        GetEvent event = SailsEventFixture.actionGet("controller", "action");
        SailsEventFixture.setContainer(event);
        assertSame(event, event.getContainer().instance(ISailsEvent.class));
        assertSame(event, event.getContainer().instance(GetEvent.class));
    }
}
