package org.opensails.viento.builtins;


public class EscapeMixin {

	public String h(Object object) {
		return EscapeMixin.escapeHtml(object);
	}
	
	public static String escapeHtml(Object object) {
		if (object == null)
			return null;
		return object.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
