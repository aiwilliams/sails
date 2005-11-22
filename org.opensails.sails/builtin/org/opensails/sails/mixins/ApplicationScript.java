package org.opensails.sails.mixins;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.url.UrlType;

class ApplicationScript extends AbstractScript {
	public ApplicationScript(ISailsEvent event, String argument) {
		super(event, argument);
	}

	protected UrlType urlType() {
		return UrlType.SCRIPT;
	}
}
