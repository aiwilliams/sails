package org.opensails.sails.validation.constraints;

import java.lang.annotation.Annotation;

import org.opensails.sails.validation.IValidator;
import org.opensails.sails.validation.InvalidPropertyException;

public class NotNullValidator implements IValidator {
	public void init(Annotation constraint) {
	// Don't need any information
	}

	public void validate(Object value) throws InvalidPropertyException {
		if (value == null) throw new InvalidPropertyException("Value was null");
	}
}
