package org.opensails.sails.url;

import org.opensails.sails.ISailsEvent;

/**
 * There is one IUrlResolver for each {@link org.opensails.sails.url.UrlType}.
 * They are discovered upon first use and cached.
 * 
 * @see org.opensails.sails.oem.BaseConfigurator#configure(UrlResolverResolver)
 * @author aiwilliams
 */
public interface IUrlResolver {
	IUrl resolve(UrlType urlType, ISailsEvent event, String urlFragmentOrAbsolute);
}
