package org.opensails.sails.tools;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class UrlForBuiltin {
	private ISailsEvent event;

	public UrlForBuiltin(ISailsEvent event) {
		this.event = event;
	}

	/**
	 * @return an url to a builtin script file
	 */
	public IUrl script(String scriptFile) {
		return event.resolve(UrlType.SCRIPT_BUILTIN, scriptFile);
	}

	/**
	 * @return an url to a builtin css file
	 */
	public IUrl style(String cssFile) {
		return event.resolve(UrlType.STYLE_BUILTIN, cssFile);
	}
}