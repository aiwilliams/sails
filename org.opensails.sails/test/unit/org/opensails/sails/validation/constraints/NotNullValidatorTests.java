package org.opensails.sails.validation.constraints;

import junit.framework.TestCase;

import org.opensails.sails.validation.InvalidPropertyException;

public class NotNullValidatorTests extends TestCase {
	public void testIt() throws Exception {
		NotNullValidator validator = new NotNullValidator();
		validator.validate("b");
		validator.validate(1);

		try {
			validator.validate(null);
			fail("Null is not valid");
		} catch (InvalidPropertyException expected) {}
	}
}
