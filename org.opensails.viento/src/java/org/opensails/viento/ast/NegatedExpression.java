package org.opensails.viento.ast;

import org.opensails.viento.Binding;

public class NegatedExpression extends Node implements Expression {
	protected Statement statement;

	public void statement(Statement statement) {
		nodeAdded(statement);
		this.statement = statement;
	}

	public Object evaluate(Binding binding) {
		return !AstUtils.unresolvedOrFalse(statement.evaluate(binding));
	}
}
