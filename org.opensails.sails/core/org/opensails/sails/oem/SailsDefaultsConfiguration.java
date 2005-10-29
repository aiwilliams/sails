package org.opensails.sails.oem;

import java.util.HashMap;

import org.apache.commons.configuration.MapConfiguration;
import org.opensails.sails.Sails;

public class SailsDefaultsConfiguration extends MapConfiguration {
	public SailsDefaultsConfiguration() {
		super(new HashMap());

		setProperty(Sails.ConfigurationKey.Url.IMAGES, "images");
		setProperty(Sails.ConfigurationKey.Url.SCRIPTS, "scripts");
		setProperty(Sails.ConfigurationKey.Url.STYLES, "styles");
		setProperty(Sails.ConfigurationKey.Url.SECURE_SCHEME, "https");
	}
}
