package org.opensails.sails.url;

import org.opensails.sails.ISailsEvent;

/**
 * An IUrlResolver is used to resolve urls in a Sails application.
 * 
 * The problem it solves is the one where generated urls must be relative to the
 * application but you don't want to know all those details in the places where
 * you need the url. The event is the common thread throughout request
 * processing, and is therefore the natural place to go to resolve urls. That is
 * fine, but we don't want to have an explosion of methods on the event. Look
 * into the {@link org.opensails.sails.ISailsEvent#resolve(UrlType, String)}
 * method.
 * 
 * This does not prevent, nor should it discourage, folks from instantiating
 * concreate {@link org.opensails.sails.url.IUrl}s. Just be sure you understand
 * why you would want to.
 * 
 * There is one IUrlResolver for each {@link org.opensails.sails.url.UrlType}.
 * They are discovered upon first use and cached.
 * 
 * @see org.opensails.sails.oem.BaseConfigurator#configure(UrlResolverResolver)
 * @author aiwilliams
 */
public interface IUrlResolver {
	IUrl resolve(UrlType urlType, ISailsEvent event, String urlFragmentOrAbsolute);
}
