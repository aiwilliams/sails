package org.opensails.sails.mixins;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.html.Script;
import org.opensails.sails.url.UrlType;

public abstract class AbstractScript {
	protected final String argument;
	protected final ISailsEvent event;

	public AbstractScript(ISailsEvent event, String argument) {
		this.event = event;
		this.argument = argument;
	}

	public BuiltinScript builtin(String argument) {
		return new BuiltinScript(event, argument);
	}

	@Override
	public String toString() {
		return new Script().src(event.resolve(urlType(), argument)).toString();
	}

	protected abstract UrlType urlType();
}
