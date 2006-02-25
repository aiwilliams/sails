package org.opensails.sails.validation;

import junit.framework.TestCase;

import org.opensails.sails.validation.oem.SailsValidationEngine;

public class SailsValidationEngineTest extends TestCase {
	public void testEngine() throws Exception {
		SailsValidationEngine engine = new SailsValidationEngine();

		ShamValidatedModel model = new ShamValidatedModel();
		model.nullFieldProperty = "Not null";
		model.lengthFieldProperty = null;
		model.trueFieldProperty = true;

		IValidationResult result = engine.validate(model);
		assertTrue(result.isValid());
		assertEquals(0, result.getInvalidProperties().length);

		model.nullFieldProperty = null;
		model.lengthFieldProperty = "";
		model.trueFieldProperty = false;

		result = engine.validate(model);
		assertFalse(result.isValid());
		assertEquals(3, result.getInvalidProperties().length);
	}
}
