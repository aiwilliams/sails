package org.opensails.sails.validation.constraints;

import org.opensails.sails.validation.IValidator;

public class NotNullValidator implements IValidator<NotNull> {
	protected String customMessage;

	public String getConstraintMessage() {
		if (!customMessage.equals(NotNull.DEFAULT_MESSAGE)) return customMessage;
		return NotNull.DEFAULT_MESSAGE;
	}

	public void init(NotNull constraint) {
		customMessage = constraint.message();
	}

	public boolean validate(Object value) {
		return value != null;
	}
}
