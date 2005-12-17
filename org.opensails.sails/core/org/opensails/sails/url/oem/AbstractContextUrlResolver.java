package org.opensails.sails.url.oem;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ContextUrl;
import org.opensails.sails.url.ExternalUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;

public abstract class AbstractContextUrlResolver implements IUrlResolver {
	public IUrl resolve(UrlType urlType, ISailsEvent event, String urlFragmentOrAbsolute) {
		if (urlFragmentOrAbsolute.contains("://")) return new ExternalUrl(event, urlFragmentOrAbsolute);

		StringBuilder extendedUrl = new StringBuilder();
		extendedUrl.append(event.getConfiguration().getString(contextRelativeConfigurationKey()));
		extendedUrl.append("/");
		extendedUrl.append(urlFragmentOrAbsolute);
		return new ContextUrl(event, extendedUrl.toString());
	}

	protected abstract String contextRelativeConfigurationKey();
}
