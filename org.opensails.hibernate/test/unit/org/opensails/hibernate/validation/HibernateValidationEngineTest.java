package org.opensails.hibernate.validation;

import junit.framework.TestCase;

import org.opensails.sails.validation.IValidationResult;

public class HibernateValidationEngineTest extends TestCase {
	public void testIt() throws Exception {
		ShamValidatedModel model = new ShamValidatedModel();
		HibernateValidationEngine engine = new HibernateValidationEngine();
		IValidationResult result = engine.validate(model);
		assertTrue(result.isValid());
		assertEquals(0, result.getInvalidProperties().length);
		model.lengthField = "";
		result = engine.validate(model);
		assertFalse(result.isValid());
		assertEquals(1, result.getInvalidProperties().length);
	}
}
