package org.opensails.functional.form;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.FormTestController;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.form.Form;

public class FormProcessingTests extends TestCase {
	public void testPostThenRender() throws Exception {
		SailsFunctionalTester tester = new SailsFunctionalTester(FormTestController.class);
		Page page = tester.post("postThenRender", FormFields.quick("model.textProperty", "hello"));
		Form form = page.form();
		form.text("model.textProperty").value("hello");
	}

	public void testRender_Model() {
		SailsFunctionalTester tester = new SailsFunctionalTester(FormTestController.class);
		Page page = tester.get("explicitExpose");
		Form form = page.form();
		assertModelRendered(form);

		page = tester.get("implicitExpose");
		assertModelRendered(form);
	}

	public void testRender_ModelDoesntHaveProperty() {
		SailsFunctionalTester tester = new SailsFunctionalTester(FormTestController.class);
		Page page = tester.get("referenceToMissingProperty");
		page.assertRenderFails("Will not render if property not supported");
	}

	public void testRender_NoModel() {
		SailsFunctionalTester tester = new SailsFunctionalTester(FormTestController.class);
		Page page = tester.get("renderNoModel");
		page.assertRenders();
	}

	private void assertModelRendered(Form form) {
		form.text("model.textProperty").value("textValue");
		form.textarea("model.textareaProperty").value("textareaValue");
		form.checkbox("model.checkboxProperty").value("checkboxValue").checked(true);
	}
}
