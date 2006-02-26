package org.opensails.viento.ast;

import java.util.ArrayList;
import java.util.List;

import org.opensails.viento.*;

public class ListLiteral extends Node implements Expression {
	protected List<Expression> list = new ArrayList<Expression>();
	
	public void add(Expression expression) {
		nodeAdded(expression);
		list.add(expression);
	}

	public Object evaluate(Binding binding) {
		List<Object> evaled = new ArrayList<Object>(list.size());
		for (Expression expression : list)
			evaled.add(nullIfUnresolvable(expression.evaluate(binding)));
		return evaled;
	}
	
	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
		for (Expression expression : list)
			expression.visit(visitor);
	}
	
	private Object nullIfUnresolvable(Object object) {
		return object instanceof UnresolvableObject ? null : object;
	}
}
