package org.opensails.sails.tester;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.fixture.controllers.HomeController;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class TestRedirectUrlTest extends TestCase {
	ShamHttpServletResponse response = new ShamHttpServletResponse();

	public void testAssertActionEquals() throws Exception {
		response.sendRedirect("home/index");
		TestRedirectUrl testRedirectUrl = new TestRedirectUrl(SailsEventFixture.actionGet(), response);
		testRedirectUrl.assertActionEquals(HomeController.class, "index");
		try {
			testRedirectUrl.assertActionEquals(BaseController.class, "index");
			throw new RuntimeException("Didn't match expectation");
		} catch (AssertionFailedError expected) {}

		testRedirectUrl = new TestRedirectUrl(SailsEventFixture.actionGet("home", "someaction"), response);
		testRedirectUrl.assertActionEquals("index");
	}

	public void testAssertMatches() throws Exception {
		response.sendRedirect("some location");
		TestRedirectUrl testRedirectUrl = new TestRedirectUrl(SailsEventFixture.actionGet(), response);
		testRedirectUrl.assertMatches("some location");
	}

	public void testConstructorShouldBlowUpIfNoRedirectHasBeenSent() {
		try {
			new TestRedirectUrl(SailsEventFixture.actionGet(), response);
			throw new RuntimeException("Constructor should blow up if no redirect has been sent.");
		} catch (AssertionFailedError expected) {}
	}

	public void testToString() throws Exception {
		response.sendRedirect("some location");
		assertEquals("some location", new TestRedirectUrl(SailsEventFixture.actionGet(), response).toString());
	}

}
