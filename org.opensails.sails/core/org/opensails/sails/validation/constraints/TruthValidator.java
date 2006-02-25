package org.opensails.sails.validation.constraints;

import java.lang.annotation.Annotation;

import org.opensails.sails.validation.IValidator;
import org.opensails.sails.validation.InvalidPropertyException;

public class TruthValidator implements IValidator {
	private Annotation constraint;

	public void init(Annotation constraint) {
		this.constraint = constraint;
	}

	public void validate(Object value) throws InvalidPropertyException {
		if (constraint.annotationType() == AssertTrue.class & Boolean.FALSE.equals(value)) throw new InvalidPropertyException("Must be true");
		else if (constraint.annotationType() == AssertFalse.class & Boolean.TRUE.equals(value)) throw new InvalidPropertyException("Must be false");
	}
}
