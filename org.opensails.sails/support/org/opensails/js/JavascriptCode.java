package org.opensails.js;

public class JavascriptCode extends JavascriptGenerator {
	private final String code;

	public JavascriptCode(String code) {
		this.code = code;
	}

	public String renderThyself() {
		return code;
	}
}
