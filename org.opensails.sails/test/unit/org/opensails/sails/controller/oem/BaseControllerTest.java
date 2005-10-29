package org.opensails.sails.controller.oem;

import junit.framework.TestCase;

import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.SailsEventFixture;

public class BaseControllerTest extends TestCase {
	public void testFieldAndFields() {
		FormFields formFields = new FormFields();
		formFields.setValue("my.field", "myValue");

		BaseController controller = new BaseController();
		controller.set(SailsEventFixture.actionPost(formFields));
		assertEquals("myValue", controller.field("my.field"));

		controller.set(SailsEventFixture.actionGet());
		assertNull(controller.field("whatever"));
	}
}
