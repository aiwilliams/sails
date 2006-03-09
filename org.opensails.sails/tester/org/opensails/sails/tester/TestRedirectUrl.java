package org.opensails.sails.tester;

import junit.framework.Assert;

import org.opensails.ezfile.EzPath;
import org.opensails.sails.Sails;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;
import org.opensails.sails.util.RegexHelper;

public class TestRedirectUrl {

	protected ShamHttpServletResponse response;

	public TestRedirectUrl(ShamHttpServletResponse response) {
		Assert.assertTrue("response.sendRedirect() should have been called.", response.wasRedirected());
		this.response = response;
	}

	public void assertActionEquals(Class<? extends IEventProcessingContext> context, String action) {
		assertMatches(EzPath.join(Sails.eventContextName(context), action));
	}

	public void assertMatches(String regex) {
		Assert.assertTrue(String.format("%s should have matched %s", destination(), regex), RegexHelper.containsMatch(destination(), regex));
	}

	public String destination() {
		return response.getRedirectDestination();
	}

	public String pathInfo() {
		CharSequence baseSailsTesterUrl = new ShamHttpServletRequest().getRequestURL();
		return destination().replace(baseSailsTesterUrl, "");
	}

	@Override
	public String toString() {
		return destination();
	}
}
