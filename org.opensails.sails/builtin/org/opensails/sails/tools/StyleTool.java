package org.opensails.sails.tools;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.template.IMixinMethod;
import org.opensails.sails.url.UrlType;

public class StyleTool implements IMixinMethod {
	protected final ISailsEvent event;

	public StyleTool(ISailsEvent event) {
		this.event = event;
	}

	public Object invoke(Object... args) {
		return new Style(args != null && args.length > 0 ? (String) args[0] : null);
	}

	public class BuiltinStyle {
		protected final String argument;

		public BuiltinStyle(String argument) {
			this.argument = argument;
		}

		@Override
		public String toString() {
			return String.format("<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\" />", event.resolve(UrlType.STYLE_BUILTIN, argument));
		}
	}

	public class Style {
		protected final String argument;

		public Style(String argument) {
			this.argument = argument;
		}

		public BuiltinStyle builtin(String argument) {
			return new BuiltinStyle(argument);
		}

		@Override
		public String toString() {
			return String.format("<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\" />", event.resolve(UrlType.STYLE, argument));
		}
	}
}
