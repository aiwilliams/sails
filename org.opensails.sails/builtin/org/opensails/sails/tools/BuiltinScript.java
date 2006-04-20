package org.opensails.sails.tools;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.html.Script;
import org.opensails.sails.template.Require;
import org.opensails.sails.url.UrlType;

public class BuiltinScript extends Script {
	protected final ISailsEvent event;
	protected Require require;

	public BuiltinScript(ISailsEvent event) {
		this.event = event;
	}

	public BuiltinScript(Require require, ISailsEvent event) {
		this.require = require;
		this.event = event;
	}

	public BuiltinScript builtin(String argument) {
		src(event.resolve(UrlType.SCRIPT_BUILTIN, argument));
		if (require != null) require.script(this);
		return this;
	}

	@Override
	public String renderThyself() {
		return require == null ? super.renderThyself() : "";
	}
}
