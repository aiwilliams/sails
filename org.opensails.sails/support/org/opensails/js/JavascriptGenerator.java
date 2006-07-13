package org.opensails.js;

import org.opensails.sails.url.IUrl;
import org.opensails.sails.util.Quick;

public abstract class JavascriptGenerator implements IJavascriptRenderable {
	/*
	 * Currently errs on the side of not quoting things, but the special cases
	 * are sure to add up...
	 */
	protected String possiblyQuoted(Object object, boolean strictJson) {
		if (object instanceof String || object instanceof Enum || object instanceof IUrl)
			return quoted(object, strictJson);
		if (object instanceof IJavascriptRenderable)
			return ((IJavascriptRenderable)object).renderThyself(strictJson);
		return String.valueOf(object);
	}
	
	protected String quoted(Object object, boolean strictJson) {
		return quoted(object, strictJson ? '"' : '\'');
	}
	
	protected String quoted(Object object, char character) {
		return Quick.string(character, escape(object), character);
	}
	
	protected String escape(Object object) {
		return String.valueOf(object).replace("'", "\\'").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}
	
	public String renderThyself() {
		return renderThyself(false);
	}

	@Override
	public String toString() {
		return renderThyself();
	}
}
