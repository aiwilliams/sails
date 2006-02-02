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

	public InputStream resolve(String identifier) {
		return getClass().getClassLoader().getResourceAsStream(moutPointRelative(identifier));
	}

	/**
	 * @param identifier relative to mountPoint i.e. 'controllers/builtin'.
	 */
	protected String moutPointRelative(String identifier) {
		if (mountPoint != null) {
			if (identifier.startsWith("/")) return String.format("%s%s", mountPoint, identifier);
			return String.format("%s/%s", mountPoint, identifier);
		}
		return identifier;
	}
}
