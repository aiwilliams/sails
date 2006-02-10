package org.opensails.sails.tester;

import junit.framework.Assert;

import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;
import org.opensails.sails.util.RegexHelper;

public class TestRedirectUrl {

	protected String redirect;

	public TestRedirectUrl(ShamHttpServletResponse response) {
		Assert.assertTrue("response.sendRedirect() should have been called.", response.wasRedirected());
		this.redirect = response.getRedirectDestination();
	}

	public void assertMatches(String regex) {
		Assert.assertTrue(String.format("{0} should have matched {1}", redirect, regex), RegexHelper.containsMatch(redirect, regex));
	}

	@Override
	public String toString() {
		return redirect;
	}

	public String pathInfo() {
		CharSequence baseSailsTesterUrl = new ShamHttpServletRequest().getRequestURL();
		return redirect.replace(baseSailsTesterUrl, "");
	}
}
