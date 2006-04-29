package org.opensails.viento.ast;

import java.util.ArrayList;
import java.util.List;

import org.opensails.viento.*;
import org.opensails.viento.UnresolvableObject.SilencedObject;

public class ListLiteral extends Node implements Expression {
	protected List<Expression> list = new ArrayList<Expression>();
	
	public void add(Expression expression) {
		nodeAdded(expression);
		list.add(expression);
	}

	public Object evaluate(Binding binding) {
		List<Object> evaled = new ArrayList<Object>(list.size());
		for (Expression expression : list) {
			Object object = expression.evaluate(binding);
			if (!(object instanceof SilencedObject))
				evaled.add(nullIfUnresolvable(object));
		}
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
