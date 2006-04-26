package org.opensails.js;

import org.opensails.sails.url.IUrl;
import org.opensails.viento.IRenderable;

public abstract class JavascriptGenerator implements IRenderable {
	/*
	 * Currently errs on the side of not quoting things, but the special cases
	 * are sure to add up...
	 */
	protected String possiblyQuoted(Object object) {
		if (object instanceof String || object instanceof Enum || object instanceof IUrl)
			return String.format("'%s'", escape(object));
		return String.valueOf(object);
	}
	
	protected String escape(Object object) {
		return String.valueOf(object).replace("'", "\\'").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}

	@Override
	public String toString() {
		return renderThyself();
	}
}
