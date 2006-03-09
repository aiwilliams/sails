package org.opensails.sails.tester;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.fixture.controllers.HomeController;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class TestRedirectUrlTest extends TestCase {
	protected ShamHttpServletResponse response = new ShamHttpServletResponse();

	public void testAssertMatches() throws Exception {
		response.sendRedirect("some location");
		TestRedirectUrl testRedirectUrl = new TestRedirectUrl(response);
		testRedirectUrl.assertMatches("some location");

		response.sendRedirect("home/index");
		testRedirectUrl.assertActionEquals(HomeController.class, "index");
		try {
			testRedirectUrl.assertActionEquals(BaseController.class, "index");
			throw new RuntimeException("Didn't match expectation");
		} catch (AssertionFailedError expected) {}
	}

	public void testConstructorShouldBlowUpIfNoRedirectHasBeenSent() {
		try {
			new TestRedirectUrl(response);
			throw new RuntimeException("Constructor should blow up if no redirect has been sent.");
		} catch (AssertionFailedError expected) {}
	}

	public void testToString() throws Exception {
		response.sendRedirect("some location");
		assertEquals("some location", new TestRedirectUrl(response).toString());
	}

}
