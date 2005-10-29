package org.opensails.sails.url.oem;

import org.opensails.sails.Sails;

public class ImageUrlResolver extends AbstractContextUrlResolver {
	@Override
	protected String contextRelativeConfigurationKey() {
		return Sails.ConfigurationKey.Url.IMAGES;
	}
}
