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
	}
}
