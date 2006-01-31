package org.opensails.sails.oem;

import java.util.HashMap;

import org.apache.commons.configuration.MapConfiguration;
import org.opensails.sails.Sails;

public class SailsDefaultsConfiguration extends MapConfiguration {
	public static final String SECURE_SCHEME = "https";
	public static final String STYLES = "styles";
	public static final String SCRIPTS = "scripts";
	public static final String IMAGES = "images";

	public SailsDefaultsConfiguration() {
		super(new HashMap());

		setProperty(Sails.ConfigurationKey.Url.IMAGES, IMAGES);
		setProperty(Sails.ConfigurationKey.Url.SCRIPTS, SCRIPTS);
		setProperty(Sails.ConfigurationKey.Url.STYLES, STYLES);
		setProperty(Sails.ConfigurationKey.Url.SECURE_SCHEME, SECURE_SCHEME);
	}
}
