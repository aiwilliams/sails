package org.opensails.sails.tester;

import junit.framework.Assert;

import org.opensails.ezfile.EzPath;
import org.opensails.sails.Sails;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;
import org.opensails.sails.url.ExternalUrl;
import org.opensails.sails.util.RegexHelper;

public class TestRedirectUrl {

	protected ShamHttpServletResponse response;
	protected ISailsEvent originatingEvent;

	public TestRedirectUrl(ISailsEvent originatingEvent, ShamHttpServletResponse response) {
		Assert.assertTrue("response.sendRedirect() should have been called.", response.wasRedirected());

		this.originatingEvent = originatingEvent;
		this.response = response;
	}

	public void assertActionEquals(Class<? extends IEventProcessingContext> context, String action) {
		assertMatches(EzPath.join(Sails.eventContextName(context), action));
	}

	public void assertActionEquals(String actionOnCurrentController) {
		assertMatches(EzPath.join(originatingEvent.getProcessorName(), actionOnCurrentController));
	}

	public void assertControllerEquals(Class<? extends IEventProcessingContext> context) {
		assertMatches(EzPath.join(Sails.eventContextName(context)));
	}

	public void assertMatches(String regex) {
		Assert.assertTrue(String.format("%s should have matched %s", destination(), regex), RegexHelper.containsMatch(destination(), regex));
	}

	public void assertQueryParamMatches(String name, String regex) {
		ExternalUrl externalUrl = new ExternalUrl(destination());
		Assert.assertTrue(String.format("%s should have matched %s", externalUrl.getQueryParam(name), regex), RegexHelper.containsMatch(destination(), regex));
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
