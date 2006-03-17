package org.opensails.sails.url.oem;

import org.opensails.sails.Sails;

public class ScriptUrlResolver extends AbstractContextUrlResolver {
	@Override
	protected String contextRelativeConfigurationKey() {
		return Sails.ConfigurationKey.Url.SCRIPTS;
	}

	@Override
	protected String modifyBeforeResolution(String url) {
		if (url.endsWith(".js")) return url;
		else return url + ".js";
	}
}
