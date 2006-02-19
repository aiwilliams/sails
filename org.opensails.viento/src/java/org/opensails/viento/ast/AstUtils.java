package org.opensails.viento.ast;

import org.opensails.viento.UnresolvableObject;

public class AstUtils {
	public static boolean unresolvedOrFalse(Object object) {
		return !(object instanceof UnresolvableObject) && !object.equals(false);
	}
	
	public static String unescapeString(String value) {
		return value.replace("\\'", "'").replace("\\\\", "\\").replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\\"", "\"").replace("\\$", "$");
	}
}
