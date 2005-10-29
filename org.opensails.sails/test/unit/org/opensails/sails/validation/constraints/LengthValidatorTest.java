package org.opensails.sails.validation.constraints;

import junit.framework.TestCase;

import org.opensails.sails.validation.InvalidPropertyException;

public class LengthValidatorTest extends TestCase {
    public void testValidate() throws Exception {
        LengthValidator validator = new LengthValidator();
        validator.min = 3;
        validator.max = 4;
        
        validator.validate(null);
        validator.validate("123");
        validator.validate("1234");
        
        try {
            validator.validate("12");
            fail("too short");
        } catch (InvalidPropertyException expected) {
        }

        try {
            validator.validate("123456");
            fail("too long");
        } catch (InvalidPropertyException expected) {
        }
    }
}
