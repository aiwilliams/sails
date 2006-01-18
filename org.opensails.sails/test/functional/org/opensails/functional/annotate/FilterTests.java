package org.opensails.functional.annotate;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.FilterTestController;
import org.opensails.sails.tester.Page;

public class FilterTests extends TestCase {
	public void testBeforeFilter_Layout() throws Exception {
		SailsFunctionalTester tester = new SailsFunctionalTester(FilterTestController.class);
		Page page = tester.get("filteredBefore");
		page.assertLayout("filterTest/layoutInBeforeMethod");
	}

	public void testBeforeFilter_Precedence() throws Exception {
		SailsFunctionalTester tester = new SailsFunctionalTester(FilterTestController.class);
		Page page = tester.get("filteredBefore");
		page.assertContains("Class filters come before method filters", "ExampleFilter#beforeActionExampleFilterTwo#beforeActionFilterTestController#beforeMethod");
		page.assertExcludes("FilterTestController#onlyFilter");

		page = tester.get("filteredExcluded");
		page.assertExcludes("ExampleFilter#beforeActionExampleFilterTwo#beforeActionFilterTestController#beforeMethod");
		page.assertExcludes("FilterTestController#onlyFilter");

		page = tester.get("filteredOnly");
		page.assertExcludes("ExampleFilter#beforeActionExampleFilterTwo#beforeActionFilterTestController#beforeMethod");
		page.assertContains("FilterTestController#onlyFilter");
	}

	public void testBeforeFilter_TerminatesProcessing() throws Exception {
		SailsFunctionalTester tester = new SailsFunctionalTester(FilterTestController.class);
		Page page = tester.get("terminated");
		page.assertContains("ExampleFilter#beforeActionFilterTestController#terminating");
	}
}
