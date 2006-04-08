package org.opensails.sails.validation.constraints;

import java.lang.annotation.Annotation;

import org.opensails.sails.validation.IValidator;

public class TruthValidator implements IValidator {
	private Annotation constraint;

	public String getConstraintMessage() {
		return String.format("Value must be %s.", constraint.annotationType() == AssertTrue.class);
	}

	public void init(Annotation constraint) {
		this.constraint = constraint;
	}

	public boolean validate(Object value) {
		if (constraint.annotationType() == AssertTrue.class & Boolean.FALSE.equals(value)) return false;
		else if (constraint.annotationType() == AssertFalse.class & Boolean.TRUE.equals(value)) return false;
		else return true;
	}
}
