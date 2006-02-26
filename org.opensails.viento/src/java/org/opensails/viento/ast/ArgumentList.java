package org.opensails.viento.ast;

import java.util.ArrayList;
import java.util.List;

import org.opensails.viento.Binding;
import org.opensails.viento.VientoVisitor;

public class ArgumentList extends Node {
	protected List<Expression> arguments = new ArrayList<Expression>();
	
	public void add(Expression expression) {
		nodeAdded(expression);
		arguments.add(expression);
	}

	public List<Object> evaluate(Binding binding) {
		List<Object> evaled = new ArrayList<Object>(arguments.size());
		for (Expression expression : arguments)
			evaled.add(expression.evaluate(binding));
		return evaled;
	}
	
	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
		for (Expression argument : arguments)
			argument.visit(visitor);
	}
}
