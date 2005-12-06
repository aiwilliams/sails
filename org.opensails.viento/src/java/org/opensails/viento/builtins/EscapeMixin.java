package org.opensails.viento.builtins;

import org.opensails.viento.Name;

public class EscapeMixin {

	@Name("$")
	public String dollar() {
		return "$";
	}
	
	/**
	 * Stopgap. Used for } and #.
	 * @param string
	 * @return string
	 */
	public String escape(String string) {
		return string;
	}
	
	public String h(Object object) {
		return EscapeMixin.escapeHtml(object);
	}
	
	public static String escapeHtml(Object object) {
		if (object == null)
			return null;
		return object.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
