package org.opensails.sails.validation.constraints;

import org.opensails.sails.SailsException;
import org.opensails.sails.validation.IValidator;

public class LengthValidator implements IValidator<Length> {
	protected int max;
	protected int min;
	protected String message;

	public String getConstraintMessage() {
		if (message != Length.DEFAULT_MESSAGE) return message;

		if (max == Integer.MAX_VALUE) return String.format("Must contain at least %d characters.", min);
		return String.format("Must contain %d to %d characters.", min, max);
	}

	public void init(Length constraint) {
		min = constraint.min();
		max = constraint.max();
		message = constraint.message();
	}

	public boolean validate(Object value) {
		if (value == null) return true;
		if (!(value instanceof String)) throw new SailsException("Length can only apply to String");

		String string = (String) value;
		int length = string.length();
		return (length >= min && length <= max);
	}
}