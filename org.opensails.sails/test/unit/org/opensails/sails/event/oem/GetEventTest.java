package org.opensails.sails.event.oem;

import junit.framework.TestCase;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.GetEvent;

public class GetEventTest extends TestCase {
	public void testSetContainer() {
		GetEvent event = SailsEventFixture.actionGet("controller", "action");
		assertSame(event, event.getContainer().instance(ISailsEvent.class));
		assertSame(event, event.getContainer().instance(GetEvent.class));
	}
}
