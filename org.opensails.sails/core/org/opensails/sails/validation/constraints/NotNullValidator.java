package org.opensails.sails.validation.constraints;

import java.lang.annotation.Annotation;

import org.opensails.sails.validation.IValidator;

public class NotNullValidator implements IValidator {
	public String getConstraintMessage() {
		return "Value cannot be null.";
	}

	public void init(Annotation constraint) {
	// Don't need any information
	}

	public boolean validate(Object value) {
		return value != null;
	}
}
