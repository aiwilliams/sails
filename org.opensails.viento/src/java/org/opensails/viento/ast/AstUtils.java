package org.opensails.viento.ast;

import org.opensails.viento.UnresolvableObject;

public class AstUtils {
	public static boolean unresolvedOrFalse(Object object) {
		return !(object instanceof UnresolvableObject) && !Boolean.FALSE.equals(object);
	}
	
	public static String unescapeString(String value) {
		return value.replace("\\'", "'").replace("\\\\", "\\").replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\\"", "\"").replace("\\$", "$");
	}
}
