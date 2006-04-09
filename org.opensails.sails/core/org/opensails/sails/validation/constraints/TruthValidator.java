package org.opensails.sails.validation.constraints;

import java.lang.annotation.Annotation;

import org.opensails.sails.validation.IValidator;

public class TruthValidator implements IValidator {
	protected Boolean expected;
	protected String customMessage;

	public String getConstraintMessage() {
		if (customMessage != null) return customMessage;
		return String.format("must have the value of %s.", expected);
	}

	public void init(Annotation constraint) {
		if (constraint.annotationType() == AssertTrue.class) {
			expected = Boolean.TRUE;
			customMessage = ((AssertTrue) constraint).message();
			if (customMessage == AssertTrue.DEFAULT_MESSAGE) customMessage = null;
		} else if (constraint.annotationType() == AssertFalse.class) {
			expected = Boolean.FALSE;
			customMessage = ((AssertFalse) constraint).message();
			if (customMessage == AssertFalse.DEFAULT_MESSAGE) customMessage = null;
		}
	}

	public boolean validate(Object value) {
		return expected.equals(value);
	}
}
