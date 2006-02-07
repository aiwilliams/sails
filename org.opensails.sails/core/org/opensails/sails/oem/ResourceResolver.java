package org.opensails.sails.oem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.url.IUrl;

/**
 * A composite resolver.
 * <p>
 * Looks through a list of IResourceResolvers. The first to return a non-null
 * value, or answer exists() == true, wins.
 * 
 * @author aiwilliams
 */
public class ResourceResolver implements IResourceResolver {
	protected List<IResourceResolver> resolvers;

	public ResourceResolver() {
		this.resolvers = new ArrayList<IResourceResolver>();
	}

	public boolean exists(IUrl applicationUrl) {
		for (IResourceResolver resolver : resolvers)
			if (resolver.exists(applicationUrl)) return true;
		return false;
	}

	public boolean exists(String identifier) {
		for (IResourceResolver resolver : resolvers)
			if (resolver.exists(identifier)) return true;
		return false;
	}

	public void push(IResourceResolver resolver) {
		resolvers.add(0, resolver);
	}

	public InputStream resolve(IUrl url) {
		for (IResourceResolver resolver : resolvers) {
			InputStream stream = resolver.resolve(url);
			if (stream != null) return stream;
		}
		return null;
	}

	public InputStream resolve(String identifier) {
		for (IResourceResolver resolver : resolvers) {
			InputStream stream = resolver.resolve(identifier);
			if (stream != null) return stream;
		}
		return null;
	}
}
