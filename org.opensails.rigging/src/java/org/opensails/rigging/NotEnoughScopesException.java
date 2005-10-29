package org.opensails.rigging;

public class NotEnoughScopesException extends RuntimeException {
	private static final long serialVersionUID = 3257009873320687152L;
	private final Enum parentScope;

	public NotEnoughScopesException(Enum parentScope) {
		this.parentScope = parentScope;
	}
	
	@Override
	public String getMessage() {
		return "There is no scope lower than <" + parentScope + "> in enum <" + parentScope.getClass() + ">";
	}
}
