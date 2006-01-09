package org.opensails.functional.annotate;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.LayoutSuperController;
import org.opensails.functional.controllers.LayoutTestController;
import org.opensails.sails.tester.Page;

public class LayoutTests extends TestCase {
	public void testBasic() throws Exception {
		SailsFunctionalTester tester = new SailsFunctionalTester(LayoutSuperController.class);
		Page page = tester.get("one");
		page.assertLayout("classSuperLayout");

		// Test that NoLayout and Layout handler used is same instance
		for (int i = 0; i < 20; i++) {
			page = tester.get("eight");
			page.assertLayout(null);
		}

		tester = new SailsFunctionalTester(LayoutTestController.class);
		page = tester.get("one");
		page.assertLayout("oneLayout");

		page = tester.get("two");
		page.assertLayout("twoSuperLayout");

		page = tester.get("three");
		page.assertLayout("classSuperLayout");

		page = tester.get("four");
		page.assertLayout("classSuperLayout");

		page = tester.get("five");
		page.assertLayout("renderTemplateLayout");

		page = tester.get("six");
		page.assertLayout(null);

		page = tester.get("seven");
		page.assertLayout(null);
	}
}
