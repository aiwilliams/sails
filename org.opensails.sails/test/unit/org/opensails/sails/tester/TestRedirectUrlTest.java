package org.opensails.sails.tester;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class TestRedirectUrlTest extends TestCase {

	protected ShamHttpServletResponse response;

	@Override
	protected void setUp() {
		response = new ShamHttpServletResponse();
	}

	public void testAssertMatches() throws Exception {
		response.sendRedirect("some location");
		new TestRedirectUrl(response).assertMatches("some location");
	}

	public void testConstructorShouldBlowUpIfNoRedirectHasBeenSent() {
		try {
			new TestRedirectUrl(response);
			throw new RuntimeException("Constructor should blow up if no redirect has been sent.");
		} catch (AssertionFailedError expected) {}
	}

}
