package org.opensails.functional.form;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.FormTestController;
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

	public void testPost_Multipart() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester(FormTestController.class);
		Page page = t.post("multipart", t.getFormFields().multipart());
		page.assertContains("made it here");

		try {
			page = t.post("multipart", t.getFormFields());
			fail("Should complain when request is not multipart. Hopefully this will save us some headaches.");
		} catch (Exception expected) {}
	}

	public void testPostThenRender() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester(FormTestController.class);

		ShamFormFields formFields = t.getFormFields();
		formFields.setValue("model.textProperty", "newTextValue");
		formFields.setValue("model.subModel.textProperty", "newTextValue");
		formFields.setValue("model.textareaProperty", "newTextareaValue");
		formFields.setValue("model.checkboxProperty", "1");
		formFields.setValue("model.checkboxListProperty", "one");
		formFields.setValue("model.radioProperty", "two");
		formFields.setValue("model.selectProperty", "third");
		formFields.setValue("model.passwordProperty", "newPasswordValue");
		formFields.setValue("model.hiddenProperty", "newHiddenValue");

		Page page = t.post("postThenRender", formFields);
		Form form = page.form();
		form.text("model.textProperty").assertValue("newTextValue");
		form.text("model.subModel.textProperty").assertValue("newTextValue");
		form.textarea("model.textareaProperty").assertValue("newTextareaValue");
		form.checkbox("model.checkboxProperty").assertChecked();
		form.checkbox("model.checkboxListProperty", "one").assertChecked();
		form.checkbox("model.checkboxListProperty", "two").assertUnchecked();
		form.radio("model.radioProperty", "one").assertUnchecked();
		form.radio("model.radioProperty", "two").assertChecked();
		form.select("model.selectProperty").assertLabelsSelected("third");
		form.password("model.passwordProperty").assertValue("");
		form.hidden("model.hiddenProperty").assertValue("newHiddenValue");
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
		page.assertRenders();
	}

	public void testRender_NoModel() {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.render("$form.start;$form.text('model.propertyOne');$form.textarea('model.propertyTwo');$form.end;");
		page.form().text("model.propertyOne").assertValue("");
		page.form().textarea("model.propertyTwo").assertValue("");
	}

	public void testSubmit() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.render("$form.start;$form.submit('Submit Value');$form.end");
		page.form().submit("Submit Value");
	}

	private void assertModelRendered(Form form) {
		form.text("model.textProperty").assertValue("textValue").assertLabeled("Text");
		form.text("model.subModel.textProperty").assertValue("textValue");
		form.textarea("model.textareaProperty").assertValue("textareaValue").assertLabeled("Textarea");
		form.checkbox("model.checkboxProperty").assertUnchecked().assertLabeled("Checkbox");
		form.checkbox("model.checkboxListProperty", "one").assertUnchecked().assertLabeled("One");
		form.checkbox("model.checkboxListProperty", "two").assertChecked().assertLabeled("Two");
		form.radio("model.radioProperty", "one").assertChecked().assertLabeled("One");
		form.radio("model.radioProperty", "two").assertUnchecked().assertLabeled("Two");
		form.select("model.selectProperty").assertLabelsSelected("selectValue").assertLabeled("Select").options().assertLabels("first", "selectValue", "third", "fourth");
		form.password("model.passwordProperty").assertValue("").assertLabeled("Password");
		form.hidden("model.hiddenProperty").assertValue("hiddenValue");
	}
}
