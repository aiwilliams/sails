package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.parser.Token;

public class InfixExpression extends Node implements Expression {
	protected Operator operator;
	protected Expression left;
	protected Expression right;

	public Object evaluate(Binding binding) {
		return operator.eval(left.evaluate(binding), right.evaluate(binding));
	}

	public void left(Expression expression) {
		this.left = expression;
		nodeAdded(expression);
	}

	public void operator(Token t) {
		token(t);
		operator = Operator.fromLiteral(t.image);
	}

	public void right(Expression expression) {
		this.right = expression;
		nodeAdded(expression);
	}

	public enum Operator {
		OR("||") {
			@Override public Object eval(Object left, Object right) {
				return AstUtils.unresolvedOrFalse(left) || AstUtils.unresolvedOrFalse(right);
			}
		},
		AND("&&") {
			@Override public Object eval(Object left, Object right) {
				return AstUtils.unresolvedOrFalse(left) && AstUtils.unresolvedOrFalse(right);
			}
		},
		EQUAL("==") {
			@Override public Object eval(Object left, Object right) {
				return safeEquals(left, right);
			}
		},
		NOT_EQUAL("!=") {
			@Override public Object eval(Object left, Object right) {
				return !safeEquals(left, right);
			}
		},
		GREATER_THAN(">") {
			@Override public Object eval(Object left, Object right) {
				return compare(left, right) > 0;
			}
		},
		LESS_THAN("<") {
			@Override public Object eval(Object left, Object right) {
				return compare(left, right) < 0;
			}
		},
		GREATER_THAN_OR_EQUAL(">=") {
			@Override public Object eval(Object left, Object right) {
				return compare(left, right) >= 0;
			}
		},
		LESS_THAN_OR_EQUAL("<=") {
			@Override public Object eval(Object left, Object right) {
				return compare(left, right) <= 0;
			}
		};

		public static Operator fromLiteral(String literal) {
			for (Operator operator : Operator.values())
				if (operator.literal.equals(literal.trim()))
					return operator;
			throw new RuntimeException("There is no infix operator [" + literal + "]");
		}

		private final String literal;
		
		private Operator(String literal) {
			this.literal = literal;
		}
		
		public abstract Object eval(Object left, Object right);
		
		@SuppressWarnings("unchecked") protected int compare(Object left, Object right) {
			return ((Comparable)left).compareTo(right);
		}

		protected boolean safeEquals(Object one, Object two) {
			return one == null ? two == null : one.equals(two);
		}
	}
}
