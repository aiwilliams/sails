package org.opensails.sails.servletapi;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class ShamHttpSession implements HttpSession {
    private Map<String, Object> attributes;

    public ShamHttpSession() {
        attributes = new HashMap<String, Object>();
    }

    public long getCreationTime() {
        return 0;
    }

    public String getId() {
        return null;
    }

    public long getLastAccessedTime() {
        return 0;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void setMaxInactiveInterval(int interval) {}

    public int getMaxInactiveInterval() {
        return 0;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Object getValue(String name) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    public String[] getValueNames() {
        return null;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void putValue(String name, Object value) {}

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void removeValue(String name) {}

    public void invalidate() {}

    public boolean isNew() {
        return false;
    }
}
