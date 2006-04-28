package org.opensails.sails.oem;

import java.util.Arrays;

import junit.framework.TestCase;

import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.url.EventUrl;

public class EventUrlTest extends TestCase {
	public void testGetAbsoluteUrlString() throws Exception {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		EventUrl event = new EventUrl(request);
		String expected = "http://localhost/shamcontext/shamservlet";
		assertEquals(expected, event.getAbsoluteUrl());

		request.setPathInfo("controller/action/122/43a;mysessionjunk=something");
		expected += "/controller/action/122/43a;mysessionjunk=something";
		assertEquals(expected, event.getAbsoluteUrl());
	}

	public void testGetController_GetAction() throws Exception {
		ShamHttpServletRequest request = new ShamHttpServletRequest();

		EventUrl event = new EventUrl(request);
		assertEquals("home", event.getController());
		assertEquals("index", event.getAction());

		request.setPathInfo("controller;mysessionjunk=something");
		event = new EventUrl(request);
		assertEquals("controller", event.getController());
		assertEquals("index", event.getAction());

		request.setPathInfo("/controller;mysessionjunk=something");
		event = new EventUrl(request);
		assertEquals("controller", event.getController());
		assertEquals("index", event.getAction());

		request.setPathInfo("controller/action/124/abc");
		event = new EventUrl(request);
		assertEquals("controller", event.getController());
		assertEquals("action", event.getAction());
		assertTrue(Arrays.equals(new String[] { "124", "abc" }, event.getActionParameters()));

		request.setPathInfo("/controller/action/122/43a");
		event = new EventUrl(request);
		assertEquals("controller", event.getController());
		assertEquals("action", event.getAction());
		assertTrue(Arrays.equals(new String[] { "122", "43a" }, event.getActionParameters()));

		request.setPathInfo("/controller/action/122/43a;mysessionjunk=something");
		event = new EventUrl(request);
		assertEquals("controller", event.getController());
		assertEquals("action", event.getAction());
		assertTrue(Arrays.equals(new String[] { "122", "43a" }, event.getActionParameters()));

		request.setPathInfo("/");
		event = new EventUrl(request);
		assertEquals("home", event.getController());
		assertEquals("index", event.getAction());
	}
}
