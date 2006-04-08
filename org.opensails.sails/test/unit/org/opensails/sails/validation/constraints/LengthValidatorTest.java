package org.opensails.sails.validation.constraints;

import junit.framework.TestCase;

public class LengthValidatorTest extends TestCase {
	public void testValidate() throws Exception {
		LengthValidator validator = new LengthValidator();
		validator.min = 3;
		validator.max = 4;

		assertTrue(validator.validate(null));
		assertTrue(validator.validate("123"));
		assertTrue(validator.validate("1234"));
		assertFalse(validator.validate("12"));
		assertFalse(validator.validate("123456"));
	}
}
