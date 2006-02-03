package org.opensails.functional.component;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.components.BasicComponent;
import org.opensails.sails.tester.Page;

public class BasicComponentTests extends TestCase {
	public void testAssets() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("componentTest", "assets", ArrayUtils.EMPTY_STRING_ARRAY);
		/**
		 * The required scripts come before the implicit scripts as the implicit
		 * scripts use the required scripts.
		 */
		page.assertMatches("Expected failure until implemented", "<script.*?src=\".*?components/assets/scripts/testing.js\".*/<script.*?src=\".*?components/assets/script.js\"");
	}

	public void testBasic() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("componentTest", "basic", ArrayUtils.EMPTY_STRING_ARRAY);
		page.assertRenders();
		page.assertContains("both from controller");
		page.assertContains("hello from controller");
		page.assertContains("both from component");
		page.assertContains("hello from component");

		page = t.get(BasicComponent.class, "someCallback");
		page.assertContains("yes, callbackMade");
	}
	
	public void testNullPassedToInitialize() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("componentTest", "null", ArrayUtils.EMPTY_STRING_ARRAY);
		page.assertRenders();
		page.assertContains("here");
	}
}
