package org.opensails.functional.form;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.FormTestController;
import org.opensails.sails.tester.Page;

public class FormProcessingTests extends TestCase {
	public void testRender_Model() {
		SailsFunctionalTester tester = new SailsFunctionalTester(FormTestController.class);
		Page page = tester.get("explicitExpose");
		page.assertContains("propertyOneValue");
		page.assertContains("propertyTwoValue");

		page = tester.get("implicitExpose");
		page.assertContains("propertyOneValue");
		page.assertContains("propertyTwoValue");
	}

	public void testRender_NoModel() {
		SailsFunctionalTester tester = new SailsFunctionalTester(FormTestController.class);
		Page page = tester.get("renderNoModel");
		page.assertRenders();
	}
}
