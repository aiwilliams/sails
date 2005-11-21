package org.opensails.sails.oem;

import org.opensails.rigging.ComponentResolver;
import org.opensails.sails.ISailsEvent;

public class FlashComponentResolver implements ComponentResolver {
	protected final ISailsEvent event;
	protected Flash flash;

	public FlashComponentResolver(ISailsEvent event) {
		this.event = event;
	}

	public Object instance() {
		if (flash == null)
			flash = Flash.load(event.getRequest(), event.getSession(false));
		return flash;
	}

	public boolean isInstantiated() {
		return flash != null;
	}

	public Class<?> type() {
		return Flash.class;
	}
}
