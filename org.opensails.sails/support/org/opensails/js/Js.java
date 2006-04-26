package org.opensails.js;

import java.util.LinkedHashMap;

import org.opensails.sails.util.Quick;

public class Js {
	public static JavascriptMethodCall methodCall(String name, Object... args) {
		return new JavascriptMethodCall(name, args);
	}

	@SuppressWarnings("unchecked")
	public static JavascriptObject object(Object... objects) {
		return new JavascriptObject((LinkedHashMap) Quick.linkedMap(objects));
	}

	public static JavascriptArray array(Object... objects) {
		return new JavascriptArray(Quick.list(objects));
	}

	public static JavascriptDomNode node(Object id) {
		return new JavascriptDomNode(id);
	}

	/**
	 * For when you've just got a String but you don't want it to be quoted.
	 */
	public static JavascriptCode literal(String code) {
		return new JavascriptCode(code);
	}
}
