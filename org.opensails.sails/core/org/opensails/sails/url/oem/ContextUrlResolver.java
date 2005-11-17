package org.opensails.sails.url.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.url.ContextUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;

public class ContextUrlResolver implements IUrlResolver {
	public IUrl resolve(UrlType urlType, ISailsEvent event, String urlFragmentOrAbsolute) {
		return new ContextUrl(event, urlFragmentOrAbsolute);
	}
}
