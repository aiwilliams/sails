package org.opensails.sails.validation.constraints;

import org.opensails.sails.SailsException;
import org.opensails.sails.validation.IValidator;

public class LengthValidator implements IValidator<Length> {
	protected int max;
	protected int min;
	protected String customMessage;

	public String getConstraintMessage() {
		if (customMessage != Length.DEFAULT_MESSAGE) return customMessage;

		if (max == Integer.MAX_VALUE) return String.format("must contain at least %d characters", min);
		return String.format("must contain %d to %d characters", min, max);
	}

	public void init(Length constraint) {
		min = constraint.min();
		max = constraint.max();
		customMessage = constraint.message();
	}

	public boolean validate(Object value) {
		if (value == null) return true;
		if (!(value instanceof String)) throw new SailsException("Length can only apply to String");

		String string = (String) value;
		int length = string.length();
		return (length >= min && length <= max);
	}
}