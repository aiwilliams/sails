package org.opensails.sails.validation.oem;

import junit.framework.TestCase;

import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.constraints.Length;
import org.opensails.sails.validation.constraints.NotNull;

public class ModelValidatorTests extends TestCase {
	public void testMultipleValidators() throws Exception {
		ModelValidator validator = new ModelValidator(MyModel.class);

		MyModel myModel = new MyModel();
		myModel.notNullAndLength = null;
		IInvalidProperty[] properties = validator.invalidProperties(myModel);
		assertEquals(1, properties.length);

		myModel.notNullAndLength = "1";
		properties = validator.invalidProperties(myModel);
		assertEquals(1, properties.length);

		myModel.notNullAndLength = "123";
		properties = validator.invalidProperties(myModel);
		assertEquals(0, properties.length);
	}

	public static class MyModel {

		@NotNull
		@Length(min = 3)
		public String notNullAndLength;

	}
}
