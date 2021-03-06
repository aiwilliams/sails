package org.opensails.sails.tester.browser;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.opensails.sails.tester.servletapi.ShamHttpSession;

public class TesterSession implements HttpSession {
	protected Browser browser;
	protected ShamHttpSession delegate;

	public TesterSession(Browser browser) {
		this(browser, false);
	}

	public TesterSession(Browser browser, boolean create) {
		this.browser = browser;
		delegate = browser.getHttpSession(create);
	}

	public void assertContains(Class name) {
		assertContains(name.getName());
	}

	public void assertContains(String key) {
		Assert.assertNotNull(String.format("Expected session to contain %s but did not", key), getDelegate(false).getAttribute(key));
	}

	public void assertExcludes(String key) {
		if (delegate == null) return;
		Assert.assertNull(String.format("Expected session not to contain %s but did", key), delegate.getAttribute(key));
	}

	public void assertExists() {
		getDelegate(false);
	}

	public void assertNull() {
		Assert.assertNull("Expected to not have an HttpSession", delegate);
	}

	public Object getAttribute(String name) {
		return getDelegate(false).getAttribute(name);
	}

	public Enumeration getAttributeNames() {
		return getDelegate(false).getAttributeNames();
	}

	public long getCreationTime() {
		return getDelegate(false).getCreationTime();
	}

	public String getId() {
		return getDelegate(false).getId();
	}

	public long getLastAccessedTime() {
		return getDelegate(false).getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return getDelegate(false).getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return getDelegate(false).getServletContext();
	}

	@SuppressWarnings("deprecation")
	public HttpSessionContext getSessionContext() {
		return getDelegate(false).getSessionContext();
	}

	public Object getValue(String name) {
		return getDelegate(false).getValue(name);
	}

	public String[] getValueNames() {
		return getDelegate(false).getValueNames();
	}

	public void invalidate() {
		getDelegate(false).invalidate();
	}

	public boolean isNew() {
		return getDelegate(false).isNew();
	}

	public void putValue(String name, Object value) {
		getDelegate(false).putValue(name, value);
	}

	public void removeAttribute(String name) {
		getDelegate(false).removeAttribute(name);
	}

	public void removeValue(String name) {
		getDelegate(false).removeValue(name);
	}

	public void setAttribute(String key, Object value) {
		getDelegate(true).setAttribute(key, value);
	}

	public void setMaxInactiveInterval(int interval) {
		getDelegate(false).setMaxInactiveInterval(interval);
	}

	protected ShamHttpSession getDelegate(boolean create) {
		if (delegate == null) {
			if (!create) throw new AssertionFailedError("There is no session available. Nothing has forced it to be created.");
			delegate = browser.getHttpSession(create);
		}
		return delegate;
	}
}