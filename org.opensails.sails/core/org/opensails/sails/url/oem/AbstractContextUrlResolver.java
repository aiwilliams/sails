package org.opensails.sails.url.oem;

import org.opensails.ezfile.EzPath;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ContextUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public abstract class AbstractContextUrlResolver extends AbstractUrlResolver {
	protected abstract String contextRelativeConfigurationKey();

	@Override
	protected IUrl resolveApplicationUrl(UrlType urlType, ISailsEvent event, String modifiedUrl) {
		return new ContextUrl(event, EzPath.join(event.getConfiguration().getString(contextRelativeConfigurationKey()), modifiedUrl));
	}
}
