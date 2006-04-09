package org.opensails.sails.validation.constraints;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class PatternValidatorTests extends TestCase {
	public void testIt() throws Exception {
		PatternValidator validator = new PatternValidator();
		validator.pattern = Pattern.compile(".*patt.*");
		assertTrue(validator.validate("me pattern"));
		assertFalse(validator.validate("me patern"));
		assertTrue(validator.validate(null));
	}
}
