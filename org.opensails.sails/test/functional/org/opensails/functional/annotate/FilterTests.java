package org.opensails.functional.annotate;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.FilterTestController;
import org.opensails.sails.tester.Page;

public class FilterTests extends TestCase {
	public void testBasic() throws Exception {
		SailsFunctionalTester tester = new SailsFunctionalTester(FilterTestController.class);
		Page page = tester.get("filteredBefore");
		page.assertMatches("Class filters come before method filters", "ExampleFilter#beforeAction.*?FilterTestController#beforeMethod");
	}
}
