package org.opensails.sails.validation.constraints;

import junit.framework.TestCase;

import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.validation.InvalidPropertyException;

public class TruthValidatorTests extends TestCase {
	public void testIsFalse() throws Exception {
		TruthValidator validator = new TruthValidator();
		validator.init(ClassHelper.methodsAnnotated(Model.class, AssertFalse.class)[0].getAnnotation(AssertFalse.class));

		validator.validate(false);
		validator.validate(Boolean.FALSE);

		try {
			validator.validate(true);
			fail("True is not valid");
		} catch (InvalidPropertyException expected) {}

		try {
			validator.validate(Boolean.TRUE);
			fail("True is not valid");
		} catch (InvalidPropertyException expected) {}
	}

	public void testIsTrue() throws Exception {
		TruthValidator validator = new TruthValidator();
		validator.init(ClassHelper.methodsAnnotated(Model.class, AssertTrue.class)[0].getAnnotation(AssertTrue.class));

		validator.validate(true);
		validator.validate(Boolean.TRUE);

		try {
			validator.validate(false);
			fail("False is not valid");
		} catch (InvalidPropertyException expected) {}

		try {
			validator.validate(Boolean.FALSE);
			fail("False is not valid");
		} catch (InvalidPropertyException expected) {}
	}

	/*
	 * This exists solely to provide annotation instances for the tests here.
	 */
	public static class Model {
		@AssertFalse
		public boolean thisIsFalse() {
			return false;
		}

		@AssertTrue
		public boolean thisIsTrue() {
			return true;
		}
	}
}
