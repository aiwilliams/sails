package org.opensails.sails;

import java.io.InputStream;

import org.opensails.sails.url.IUrl;

public interface IResourceResolver {
	boolean exists(IUrl applicationUrl);

	boolean exists(String identifier);

	InputStream resolve(String identifier);
}
