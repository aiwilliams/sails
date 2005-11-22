package org.opensails.sails.mixins;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.url.UrlType;

class BuiltinScript extends AbstractScript {
	public BuiltinScript(ISailsEvent event, String argument) {
		super(event, argument);
	}

	@Override
	protected UrlType urlType() {
		return UrlType.SCRIPT_BUILTIN;
	}
}
