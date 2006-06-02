package org.opensails.sails.oem;

import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;

public class HostFragmentKey extends FragmentKey {

	public HostFragmentKey(ISailsEvent event) {
		super(event);
		value = String.format("%s/%s", event.getUrl().getHostname(), value);
	}

	public HostFragmentKey(ISailsEvent event, String name) {
		super(event, name);
		value = String.format("%s/%s", event.getUrl().getHostname(), value);
	}

	public HostFragmentKey(String hostname, Class<? extends IEventProcessingContext> context, String action) {
		super(context, action);
		value = String.format("%s/%s", hostname, value);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	@Override
	public String toString() {
		return super.toString();
	}
}
