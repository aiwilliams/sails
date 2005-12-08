package org.opensails.sails.mixins;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.html.Script;
import org.opensails.sails.template.IMixinMethod;
import org.opensails.sails.url.UrlType;
import org.opensails.viento.Block;

public class ScriptMixin implements IMixinMethod<Script> {
	protected final ISailsEvent event;

	public ScriptMixin(ISailsEvent event) {
		this.event = event;
	}

	public Script invoke(Object... args) {
		if (args == null || args.length == 0) return new BuiltinScript(event);
		if (args[0].getClass().equals(String.class)) return new Script().src(event.resolve(UrlType.SCRIPT, (String) args[0]));
		if (args[0].getClass().equals(Block.class)) return new Script().inline((Block) args[0]);
		throw new IllegalArgumentException("Only URL String or script code Block accepted");
	}
}
