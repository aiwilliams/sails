package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.VientoVisitor;

public class MapEntry extends Node {
	protected Expression key;
	protected Expression value;

	public void key(Expression expression) {
		nodeAdded(expression);
		this.key = expression;
	}
	
	public void value(Expression expression) {
		nodeAdded(expression);
		this.value = expression;
	}

	public Object evalKey(Binding binding) {
		return key.evaluate(binding);
	}

	public Object evalValue(Binding binding) {
		return value.evaluate(binding);
	}
	
	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
		key.visit(visitor);
		value.visit(visitor);
	}
}
