/*
 * Created on Feb 10, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.oem;

import java.io.InputStream;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.url.IUrl;

/**
 * Resolves resources by looking in the classpath.
 */
public class ClasspathResourceResolver implements IResourceResolver {
	protected String mountPoint;

	public ClasspathResourceResolver() {}

	/**
	 * @param mountPoint classpath root in the form of 'org/opensails/sails'.
	 */
	public ClasspathResourceResolver(String mountPoint) {
		this.mountPoint = mountPoint;
	}

	public boolean exists(IUrl applicationUrl) {
		return false;
	}

	public boolean exists(String identifier) {
		return resolve(identifier) != null;
	}

	/**
	 * @param identifier relative to mountPoint i.e. 'controllers/builtin'.
	 */
	public InputStream resolve(String identifier) {
		if (mountPoint != null) identifier = String.format("%s/%s", mountPoint, identifier);
		return getClass().getClassLoader().getResourceAsStream(identifier);
	}
}
