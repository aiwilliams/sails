package org.opensails.sails.validation.constraints;

import junit.framework.TestCase;

public class NotNullValidatorTests extends TestCase {
	public void testIt() throws Exception {
		NotNullValidator validator = new NotNullValidator();
		assertTrue(validator.validate("b"));
		assertTrue(validator.validate(1));
		assertFalse(validator.validate(null));
	}
}
