package org.opensails.sails.url.oem;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ContextUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class BuiltinScriptUrlResolver extends AbstractUrlResolver {
	@Override
	protected String modifyBeforeResolution(String url) {
		if (url.endsWith(".js")) return url;
		else return url + ".js";
	}

	@Override
	protected IUrl resolveApplicationUrl(UrlType urlType, ISailsEvent event, String modifiedUrl) {
		return new ContextUrl(event, String.format("common/scripts/%s", modifiedUrl));
	}
}
