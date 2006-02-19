package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.parser.Token;

public class StringLiteral extends Node implements Expression {
	String value;
	public void text(Token t) {
		token(t);
		value = t.image;
	}
	
	public Object evaluate(Binding binding) {
		return value == null ? "" : AstUtils.unescapeString(value);
	}
}
