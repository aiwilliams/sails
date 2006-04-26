package org.opensails.js;

public class JavascriptDomNode extends JavascriptGenerator {
	private final Object id;

	public JavascriptDomNode(Object id) {
		this.id = id;
	}

	public String renderThyself() {
		return String.format("$(%s)", possiblyQuoted(id));
	}
}
