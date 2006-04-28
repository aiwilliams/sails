package org.opensails.sails.url.oem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;

public class ControllerUrlResolver implements IUrlResolver {
	protected static final Pattern CONTROLLER_ACTION = Pattern.compile("(\\w)[\\|/](\\w)");

	public IUrl resolve(UrlType urlType, ISailsEvent event, String urlFragment) {
		Matcher m = CONTROLLER_ACTION.matcher(urlFragment);
		if (m.matches()) return new ActionUrl(event, m.group(1), m.group(2));
		return new ActionUrl(event, urlFragment);
	}
}
