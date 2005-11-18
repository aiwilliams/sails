package org.opensails.sails.controller.oem;

import junit.framework.TestCase;

import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;

public class BaseControllerTest extends TestCase {
	public void testFieldAndFields() {
		FormFields formFields = new FormFields();
		formFields.setValue("my.field", "myValue");

		BaseController controller = new BaseController();
		controller.set(SailsEventFixture.actionPost(formFields), null);
		assertEquals("myValue", controller.field("my.field"));

		controller.set(SailsEventFixture.actionGet(), null);
		assertNull(controller.field("whatever"));
	}

	public void testGetTemplateResult() throws Exception {
		BaseController controller = new BaseController();
		controller.set(SailsEventFixture.sham(), null);
		TemplateActionResult templateResult = controller.getTemplateResult();
		assertSame(templateResult, controller.getTemplateResult());
	}

	public void testRenderTemplate() throws Exception {
		BaseController controller = new BaseController();
		GetEvent actionGet = SailsEventFixture.actionGet();
		controller.set(actionGet, null);
		TemplateActionResult result = controller.renderTemplate("justTheActionName");
		assertEquals(String.format("%s/%s", actionGet.getControllerName(), "justTheActionName"), result.getIdentifier());
	}
}
