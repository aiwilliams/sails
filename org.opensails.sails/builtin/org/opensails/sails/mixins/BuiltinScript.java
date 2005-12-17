package org.opensails.sails.mixins;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.html.Script;
import org.opensails.sails.url.UrlType;

public class BuiltinScript extends Script {
	private final ISailsEvent event;

	public BuiltinScript(ISailsEvent event) {
		this.event = event;
	}

	public BuiltinScript builtin(String argument) {
		return (BuiltinScript) src(event.resolve(UrlType.SCRIPT_BUILTIN, argument));
	}
}
