package org.opensails.sails.tools;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.html.Script;
import org.opensails.sails.template.IMixinMethod;
import org.opensails.sails.url.UrlType;
import org.opensails.viento.Block;

public class ScriptTool implements IMixinMethod<Script> {
	protected final ISailsEvent event;

	public ScriptTool(ISailsEvent event) {
		this.event = event;
	}

	public Script invoke(Object... args) {
		if (args == null || args.length == 0) return new BuiltinScript(event);
		if (args[0].getClass().equals(String.class)) return new Script(event.resolve(UrlType.SCRIPT, (String) args[0]));
		if (args[0].getClass().equals(Block.class)) return new Script((Block) args[0]);
		throw new IllegalArgumentException("Only URL String or script code Block accepted");
	}
}
