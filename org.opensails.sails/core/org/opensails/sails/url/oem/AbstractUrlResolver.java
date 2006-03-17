package org.opensails.sails.url.oem;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ExternalUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;

public abstract class AbstractUrlResolver implements IUrlResolver {

	public IUrl resolve(UrlType urlType, ISailsEvent event, String urlFragmentOrAbsolute) {
		if (urlFragmentOrAbsolute.contains("://")) return new ExternalUrl(event, urlFragmentOrAbsolute);
		String modifiedUrl = modifyBeforeResolution(urlFragmentOrAbsolute);
		return resolveApplicationUrl(urlType, event, modifiedUrl);
	}

	protected String modifyBeforeResolution(String url) {
		return url;
	}

	protected abstract IUrl resolveApplicationUrl(UrlType urlType, ISailsEvent event, String modifiedUrl);
}
