package org.opensails.sails.oem;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.url.ContextUrl;
import org.opensails.sails.url.IUrl;

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

	public boolean exists(IUrl applicationUrl) {
		if (!(applicationUrl instanceof ContextUrl)) return false;
		ContextUrl contextUrl = (ContextUrl) applicationUrl;
		return resolve(contextUrl.getContextRelativePath()) != null;
	}

	public boolean exists(String identifier) {
		return resolve(identifier) != null;
	}

	public InputStream resolve(String identifier) {
		return servletContext.getResourceAsStream(mountPoint + "/" + identifier);
	}
}
