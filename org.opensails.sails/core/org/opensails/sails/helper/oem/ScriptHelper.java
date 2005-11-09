package org.opensails.sails.helper.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.helper.IHelperMethod;
import org.opensails.sails.url.UrlType;

public class ScriptHelper implements IHelperMethod {
	protected final ISailsEvent event;

	public ScriptHelper(ISailsEvent event) {
		this.event = event;
	}

	public Object invoke(Object... args) {
		return new Script(args != null && args.length > 0 ? (String) args[0] : null);
	}

	public class BuiltinScript {
		protected final String argument;

		public BuiltinScript(String argument) {
			this.argument = argument;
		}

		@Override
		public String toString() {
			return String.format("<script src=\"%s\"></script>", event.resolve(UrlType.SCRIPT_BUILTIN, argument));
		}
	}

	public class Script {
		protected final String argument;

		public Script(String argument) {
			this.argument = argument;
		}

		public BuiltinScript builtin(String argument) {
			return new BuiltinScript(argument);
		}

		@Override
		public String toString() {
			return String.format("<script src=\"%s\"></script>", event.resolve(UrlType.SCRIPT, argument));
		}
	}
}
