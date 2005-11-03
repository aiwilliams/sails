package org.opensails.sails.tester;

import junit.framework.Assert;

import org.opensails.sails.servletapi.ShamHttpSession;

public class TestSession {
	protected final TestableSailsApplication application;

	public TestSession(TestableSailsApplication application) {
		this.application = application;
	}

	public void assertContains(String key) {
		assertSession();
		Assert.assertNotNull(String.format("Expected session to contain %s but did not", key), getSession().getAttribute(key));
	}

	public void assertExcludes(String key) {
		if (getSession() == null) return;
		Assert.assertNull(String.format("Expected session not to contain %s but did", key), getSession().getAttribute(key));
	}

	public void setAttribute(String key, Object value) {
		getSession(true).setAttribute(key, value);
	}

	protected void assertSession() {
		Assert.assertNotNull("No session is available", getSession());
	}

	protected ShamHttpSession getSession() {
		return getSession(false);
	}

	protected ShamHttpSession getSession(boolean create) {
		return application.getSession(create);
	}
}