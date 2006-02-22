package org.opensails.functional.form;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.FormTestController;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.browser.ShamFileUpload;
import org.opensails.sails.tester.browser.ShamFormFields;
import org.opensails.sails.tester.form.Form;

public class FormProcessingTests extends TestCase {
	public void testPost_FileUpload() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester(FormTestController.class);
		ShamFormFields fields = t.getFormFields();
		fields.addFile("aFileField", new ShamFileUpload("theFileName.txt", "the content"));
		Page page = t.post("fileUpload", fields);
		page.assertContains("the content");
	}

	public void testPostThenRender() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester(FormTestController.class);
		Page page = t.post("postThenRender", FormFields.quick("model.textProperty", "hello"));
		Form form = page.form();
		form.text("model.textProperty").value("hello");
	}

	public void testRender_Model() {
		SailsFunctionalTester t = new SailsFunctionalTester(FormTestController.class);
		Page page = t.get("explicitExpose");
		Form form = page.form();
		assertModelRendered(form);

		page = t.get("implicitExpose");
		assertModelRendered(form);
	}

	public void testRender_ModelDoesntHaveProperty() {
		SailsFunctionalTester t = new SailsFunctionalTester(FormTestController.class);
		Page page = t.get("referenceToMissingProperty");
		page.assertRenderFails("Will not render if property not supported");
	}

	public void testRender_NoModel() {
		SailsFunctionalTester t = new SailsFunctionalTester(FormTestController.class);
		Page page = t.get("renderNoModel");
		page.assertRenders();
	}

	private void assertModelRendered(Form form) {
		form.text("model.textProperty").value("textValue");
		form.textarea("model.textareaProperty").value("textareaValue");
		form.checkbox("model.checkboxProperty").value("checkboxValue").checked(true);
	}
}
