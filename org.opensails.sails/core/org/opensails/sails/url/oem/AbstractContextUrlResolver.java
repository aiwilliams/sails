package org.opensails.sails.url.oem;

import org.opensails.ezfile.EzPath;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ContextUrl;
import org.opensails.sails.url.ExternalUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;

public abstract class AbstractContextUrlResolver implements IUrlResolver {
	public IUrl resolve(UrlType urlType, ISailsEvent event, String urlFragmentOrAbsolute) {
		if (urlFragmentOrAbsolute.contains("://")) return new ExternalUrl(event, urlFragmentOrAbsolute);
		return new ContextUrl(event, EzPath.join(event.getConfiguration().getString(contextRelativeConfigurationKey()), urlFragmentOrAbsolute));
	}

	protected abstract String contextRelativeConfigurationKey();
}
