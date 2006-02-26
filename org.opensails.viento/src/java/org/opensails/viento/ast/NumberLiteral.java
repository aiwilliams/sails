package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.VientoVisitor;
import org.opensails.viento.parser.Token;

public class NumberLiteral extends Node implements Expression {
	int value;
	public NumberLiteral(Token t) {
		token(t);
		value = Integer.parseInt(t.image);
	}
	
	public Object evaluate(Binding binding) {
		return value;
	}

	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
	}
}
