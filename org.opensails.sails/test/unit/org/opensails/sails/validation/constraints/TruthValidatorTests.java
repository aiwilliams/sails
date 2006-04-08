package org.opensails.sails.validation.constraints;

import junit.framework.TestCase;

import org.opensails.sails.util.ClassHelper;

public class TruthValidatorTests extends TestCase {
	public void testIsFalse() throws Exception {
		TruthValidator validator = new TruthValidator();
		validator.init(ClassHelper.methodsAnnotated(Model.class, AssertFalse.class)[0].getAnnotation(AssertFalse.class));

		assertTrue(validator.validate(false));
		assertTrue(validator.validate(Boolean.FALSE));
		assertFalse(validator.validate(true));
		assertFalse(validator.validate(Boolean.TRUE));
	}

	public void testIsTrue() throws Exception {
		TruthValidator validator = new TruthValidator();
		validator.init(ClassHelper.methodsAnnotated(Model.class, AssertTrue.class)[0].getAnnotation(AssertTrue.class));

		assertTrue(validator.validate(true));
		assertTrue(validator.validate(Boolean.TRUE));
		assertFalse(validator.validate(false));
		assertFalse(validator.validate(Boolean.FALSE));
	}

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
