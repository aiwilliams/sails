package org.opensails.sails.oem;

import junit.framework.TestCase;

public class EventServletRequestTest extends TestCase {
	public void testGetSession() {
		ShamEvent event = SailsEventFixture.sham();
		EventServletRequest request = (EventServletRequest) event.req;
		assertNull(event.getSession(false));
		request.getSession();
		assertNotNull(event.getSession(false));
		assertTrue(event.sessionCreatedCalled);

		event.sessionCreatedCalled = false;
		request.getSession();
		assertFalse(event.sessionCreatedCalled);
	}

	public void testGetSessionBoolean() {
		ShamEvent event = SailsEventFixture.sham();
		EventServletRequest request = (EventServletRequest) event.req;
		assertNull(event.getSession(false));
		request.getSession(false);
		assertNull(event.getSession(false));
		assertFalse(event.sessionCreatedCalled);

		request.getSession(true);
		assertNotNull(event.getSession(false));
		assertTrue(event.sessionCreatedCalled);

		event.sessionCreatedCalled = false;
		request.getSession(true);
		assertFalse(event.sessionCreatedCalled);
	}
}
