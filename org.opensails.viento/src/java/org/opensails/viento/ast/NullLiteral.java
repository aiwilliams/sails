package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.parser.Token;

public class NullLiteral extends Node implements Expression {
	public NullLiteral(Token t) {
		token(t);
	}

	public Object evaluate(Binding binding) {
		return null;
	}
}
