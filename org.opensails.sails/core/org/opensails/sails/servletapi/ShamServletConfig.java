package org.opensails.sails.servletapi;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class ShamServletConfig implements ServletConfig {
    protected ShamServletContext context;
    protected Map<String, String> initParameters;

    public ShamServletConfig() {
        this(new ShamServletContext());
    }

    public ShamServletConfig(ShamServletContext aContext) {
        context = aContext;
        initParameters = new HashMap<String, String>();
    }

    public String getServletName() {
        return null;
    }

    public ServletContext getServletContext() {
        return context;
    }

    public String getInitParameter(String name) {
        return (String) initParameters.get(name);
    }

    public Enumeration getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }

    public void setInitParameter(String paramName, String paramValue) {
        initParameters.put(paramName, paramValue);
    }
}
