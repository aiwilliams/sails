package org.opensails.sails.validation;

import junit.framework.TestCase;

import org.opensails.sails.validation.oem.SailsValidationEngine;

public class SailsValidationEngineTest extends TestCase {
	public void testEngine() throws Exception {
		ShamValidatedModel model = new ShamValidatedModel();
		SailsValidationEngine engine = new SailsValidationEngine();
		IValidationResult result = engine.validate(model);
		assertTrue(result.isValid());
		assertEquals(0, result.getInvalidProperties().length);

        model.propertyNoSetter = "";
        result = engine.validate(model);
        assertFalse(result.isValid());
        assertEquals(1, result.getInvalidProperties().length);
	}
}
