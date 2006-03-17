package org.opensails.sails.url;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.spyglass.SpyGlass;

public class UrlResolver implements IUrlResolver {
	protected final Map<UrlType, IUrlResolver> resolverCache;

	public UrlResolver() {
		this.resolverCache = new HashMap<UrlType, IUrlResolver>();
	}

	public IUrl resolve(UrlType urlType, ISailsEvent event, String url) {
		IUrlResolver urlResolver = resolverCache.get(urlType);
		if (urlResolver == null) {
			urlResolver = SpyGlass.instantiate(urlType.getResolverClass());
			resolverCache.put(urlType, urlResolver);
		}
		return urlResolver.resolve(urlType, event, url);
	}
}
