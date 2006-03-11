package org.opensails.sails.tester.servletapi;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.opensails.sails.util.BleedingEdgeException;

public class ShamHttpSession implements HttpSession {
	private Map<String, Object> attributes;

	public ShamHttpSession() {
		attributes = new HashMap<String, Object>();
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public Enumeration getAttributeNames() {
		return Collections.enumeration(attributes.keySet());
	}

	public long getCreationTime() {
		throw new BleedingEdgeException("not quite done yet");
	}

	public String getId() {
		throw new BleedingEdgeException("not quite done yet");
	}

	public long getLastAccessedTime() {
		throw new BleedingEdgeException("not quite done yet");
	}

	public int getMaxInactiveInterval() {
		throw new BleedingEdgeException("not quite done yet");
	}

	public ServletContext getServletContext() {
		throw new BleedingEdgeException("not quite done yet");
	}

	public HttpSessionContext getSessionContext() {
		throw new BleedingEdgeException("not quite done yet");
	}

	public Object getValue(String name) {
		throw new BleedingEdgeException("not quite done yet");
	}

	public String[] getValueNames() {
		throw new BleedingEdgeException("not quite done yet");
	}

	public void invalidate() {
		throw new BleedingEdgeException("not quite done yet");
	}

	public boolean isNew() {
		return false;
	}

	public void putValue(String name, Object value) {
		throw new BleedingEdgeException("not quite done yet");
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public void removeValue(String name) {
		throw new BleedingEdgeException("not quite done yet");
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	public void setMaxInactiveInterval(int interval) {
		throw new BleedingEdgeException("not quite done yet");
	}
}
