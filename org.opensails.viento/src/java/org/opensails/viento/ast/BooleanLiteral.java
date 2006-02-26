package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.VientoVisitor;
import org.opensails.viento.parser.Token;

public class BooleanLiteral extends Node implements Expression {
	boolean value;
	
	public BooleanLiteral(Token t) {
		token(t);
		value = Boolean.parseBoolean(t.image);
	}

	public Object evaluate(Binding binding) {
		return value;
	}
	
	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
	}
}
