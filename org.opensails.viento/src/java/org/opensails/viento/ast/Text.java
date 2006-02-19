package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.parser.Token;

public class Text extends BodyNode {
	String text;
	
	public Text(Token t) {
		token(t);
		text = t.image;
	}

	@Override
	public void evaluate(Binding binding, StringBuilder s) {
		s.append(unescape(text));
	}
	
	protected String unescape(String value) {
		return value.replaceAll("\\\\([$\\\\])", "$1");
	}
}
