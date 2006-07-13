package org.opensails.js;

public class JavascriptMethodCall extends JavascriptGenerator {
	private final String name;
	private final Object[] args;

	public JavascriptMethodCall(String name, Object... args) {
		this.name = name;
		this.args = args;
	}
	
	public String renderThyself(boolean strictJson) {
		StringBuilder b = new StringBuilder();
		b.append(name);
		b.append("(");
		boolean f = true;
		for (Object arg : args) {
			if (!f || (f = false)) b.append(",");
			b.append(possiblyQuoted(arg, strictJson));
		}
		b.append(")");
		return b.toString();
	}
}
