package org.opensails.js;

import org.opensails.viento.IRenderable;

public abstract class JavascriptGenerator implements IRenderable {
	public static String possiblyQuoted(Object object) {
		if (object instanceof String || object instanceof Enum)
			return String.format("'%s'", escape(object));
		return String.valueOf(object);
	}
	
	public static String escape(Object object) {
		return String.valueOf(object).replace("'", "\\'").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}
}
