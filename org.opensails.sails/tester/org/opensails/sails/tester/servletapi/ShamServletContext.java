package org.opensails.sails.tester.servletapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ShamServletContext implements ServletContext {
    protected Map<String, String> initParameters = new HashMap<String, String>();
    protected Map<String, Object> attributes = new HashMap<String, Object>();
    protected String contextPath = "/your/context/path";
    protected File contextDirectory;

    public ShamServletContext() {}

    public ShamServletContext(File contextDirectory) {
        this.contextDirectory = contextDirectory;
    }

    public ServletContext getContext(String uripath) {
        return null;
    }

    public int getMajorVersion() {
        return 0;
    }

    public int getMinorVersion() {
        return 0;
    }

    public String getMimeType(String file) {
        return null;
    }

    public Set getResourcePaths(String path) {
        return null;
    }

    public URL getResource(String path) throws MalformedURLException {
        return null;
    }

    public InputStream getResourceAsStream(String path) {
        if (contextDirectory == null) return null;
        try {
            return new FileInputStream(new File(contextDirectory.getAbsolutePath() + path));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public RequestDispatcher getNamedDispatcher(String name) {
        return null;
    }

    public Servlet getServlet(String name) throws ServletException {
        return null;
    }

    public Enumeration getServlets() {
        return null;
    }

    public Enumeration getServletNames() {
        return null;
    }

    public void log(String msg) {}

    public void log(Exception exception, String msg) {}

    public void log(String message, Throwable throwable) {}

    public String getRealPath(String path) {
        return contextPath + File.separator + path;
    }

    public void setContextPath(String shamContextPath) {
        contextPath = shamContextPath;
    }

    public String getServerInfo() {
        return null;
    }

    public String getInitParameter(String name) {
        return (String) initParameters.get(name);
    }

    public Enumeration getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    public void removeAttribute(String name) {}

    public String getServletContextName() {
        return null;
    }

    public void setInitParameter(String paramName, String paramValue) {
        initParameters.put(paramName, paramValue);
    }

}