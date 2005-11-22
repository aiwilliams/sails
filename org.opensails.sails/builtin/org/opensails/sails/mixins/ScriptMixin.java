package org.opensails.sails.mixins;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.template.IMixinMethod;

public class ScriptMixin implements IMixinMethod<AbstractScript> {
	protected final ISailsEvent event;

	public ScriptMixin(ISailsEvent event) {
		this.event = event;
	}

	public AbstractScript invoke(Object... args) {
		return new ApplicationScript(event, args != null && args.length > 0 ? (String) args[0] : null);
	}
}


