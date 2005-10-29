package org.opensails.sails.url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.util.EnumHelper;
import org.opensails.sails.util.IClassResolver;

public class UrlResolver implements IUrlResolver {
	protected final Map<UrlType, IUrlResolver> resolverCache;
	protected final List<IClassResolver<IUrlResolver>> resolvers;

	public UrlResolver() {
		this.resolvers = new ArrayList<IClassResolver<IUrlResolver>>();
		this.resolverCache = new HashMap<UrlType, IUrlResolver>();
	}

	public void push(IClassResolver<IUrlResolver> resolver) {
		resolvers.add(0, resolver);
	}

	public IUrl resolve(UrlType urlType, ISailsEvent event, String url) {
		IUrlResolver urlResolver = resolverCache.get(urlType);
		if (urlResolver == null) {
			for (IClassResolver<IUrlResolver> resolver : resolvers) {
				Class<? extends IUrlResolver> urlResolverClass = resolver.resolve(EnumHelper.titleCase(urlType));
				if (urlResolverClass != null) {
					urlResolver = ClassHelper.instantiate(urlResolverClass);
					resolverCache.put(urlType, urlResolver);
					break;
				}
			}
			if (urlResolver == null) throw new IllegalArgumentException("Could not resolve an " + IUrlResolver.class + " implementation for " + urlType);
		}
		return urlResolver.resolve(urlType, event, url);
	}
}
