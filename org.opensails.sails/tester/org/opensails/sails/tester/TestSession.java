package org.opensails.sails.tester;

import junit.framework.Assert;

import org.opensails.sails.tester.servletapi.ShamHttpSession;

public class TestSession {
	protected final SailsTester sessionProvider;

	public TestSession(SailsTester sessionProvider) {
		this.sessionProvider = sessionProvider;
	}

	public void assertContains(Class name) {
		assertContains(name.getName());
	}

	public void assertContains(String key) {
		assertExists();
		Assert.assertNotNull(String.format("Expected session to contain %s but did not", key), getSession().getAttribute(key));
	}

	public void assertExcludes(String key) {
		if (getSession() == null) return;
		Assert.assertNull(String.format("Expected session not to contain %s but did", key), getSession().getAttribute(key));
	}

	public void assertNull() {
		Assert.assertNull("Session is available", getSession());
	}

	public void assertExists() {
		Assert.assertNotNull("No session is available", getSession());
	}

	public void setAttribute(String key, Object value) {
		getSession(true).setAttribute(key, value);
	}

	protected ShamHttpSession getSession() {
		return getSession(false);
	}

	protected ShamHttpSession getSession(boolean create) {
		return sessionProvider.getSession(create);
	}
}
