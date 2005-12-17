package org.opensails.sails.event.oem;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.ExceptionEvent;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class ExceptionEventTest extends TestCase {
	public void testInitialize() throws Exception {
		ISailsEvent originatingEvent = SailsEventFixture.actionGet("controller", "action");
		ExceptionEvent event = new ExceptionEvent(originatingEvent, new RuntimeException());
		assertEquals("error", event.getProcessorName());
		assertEquals("exception", event.getActionName());
		assertNotNull(event.getApplication());
		assertSame(originatingEvent.getApplication(), event.getApplication());

		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ((ShamHttpServletResponse) originatingEvent.getResponse()).getStatus());
	}

	public void testSetContainer() {
		ExceptionEvent event = SailsEventFixture.actionException("controller", "action");
		assertSame(event.getOriginatingEvent(), event.getContainer().instance(ISailsEvent.class));
		assertSame(event.getOriginatingEvent(), event.getContainer().instance(event.getOriginatingEvent().getClass()));
		assertSame(event, event.getContainer().instance(ExceptionEvent.class));
	}
}
