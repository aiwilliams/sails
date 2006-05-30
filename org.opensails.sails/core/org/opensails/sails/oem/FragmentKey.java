package org.opensails.sails.oem;

import org.opensails.sails.Sails;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;

public class FragmentKey {
	protected String value;

	public FragmentKey(Class<? extends IEventProcessingContext> context, String action) {
		value = String.format("%s/%s", Sails.eventContextName(context), action);
	}

	public FragmentKey(ISailsEvent event) {
		value = event.getContextIdentifier();
	}

	public FragmentKey(ISailsEvent event, String name) {
		value = String.format("%s/%s", event.getContextIdentifier(), name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != FragmentKey.class) return false;
		return value.equals(((FragmentKey) obj).value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return value;
	}
}
