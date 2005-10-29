package org.opensails.sails.oem;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.opensails.sails.IResourceResolver;

/**
 * Looks in the ServletContext for resources.
 */
public class ServletContextResourceResolver implements IResourceResolver {
    protected final String mountPoint;
    protected final ServletContext servletContext;

    public ServletContextResourceResolver(ServletContext servletContext, String mountPoint) {
        this.servletContext = servletContext;
        this.mountPoint = mountPoint;
    }

    public InputStream resolve(String identifier) {
        return servletContext.getResourceAsStream(mountPoint + "/" + identifier);
    }
}
