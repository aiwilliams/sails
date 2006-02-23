package org.opensails.viento.builtins;

import org.opensails.viento.Name;

public class IfMixin {
	/**
	 * @param condition
	 * @param object
	 *            to output, usually a Block
	 * @return an If
	 */
	@Name("if")
	public If create(Object condition, Object body) {
		return new If().condition(condition, body);
	}

	public class If {
		Object bodyToEvaluate;

		@Name("elseif")
		public If condition(Object condition, Object body) {
			if (condition != null && !Boolean.FALSE.equals(condition) && bodyToEvaluate == null)
				bodyToEvaluate = body;
			return this;
		}

		@Name("else")
		public If noCondition(Object body) {
			if (bodyToEvaluate == null)
				bodyToEvaluate = body;
			return this;
		}

		@Override
		public String toString() {
			return bodyToEvaluate == null ? "" : bodyToEvaluate.toString();
		}
	}
}
