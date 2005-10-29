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
}
