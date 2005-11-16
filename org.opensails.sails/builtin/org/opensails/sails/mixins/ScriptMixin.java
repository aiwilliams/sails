package org.opensails.sails.mixins;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.template.IMixinMethod;
import org.opensails.sails.url.UrlType;

public class ScriptMixin implements IMixinMethod<AbstractScript> {
	protected final ISailsEvent event;

	public ScriptMixin(ISailsEvent event) {
		this.event = event;
	}

	public AbstractScript invoke(Object... args) {
		return new ApplicationScript(event, args != null && args.length > 0 ? (String) args[0] : null);
	}
}

class ApplicationScript extends AbstractScript {
	public ApplicationScript(ISailsEvent event, String argument) {
		super(event, argument);
	}

	protected UrlType urlType() {
		return UrlType.SCRIPT;
	}
}

class BuiltinScript extends AbstractScript {
	public BuiltinScript(ISailsEvent event, String argument) {
		super(event, argument);
	}

	@Override
	protected UrlType urlType() {
		return UrlType.SCRIPT_BUILTIN;
	}
}

abstract class AbstractScript {
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
		return String.format("<script type=\"text/javascript\" src=\"%s\"></script>", event.resolve(urlType(), argument));
	}

	protected abstract UrlType urlType();
}
