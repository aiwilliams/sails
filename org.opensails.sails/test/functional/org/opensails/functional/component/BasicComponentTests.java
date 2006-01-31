package org.opensails.functional.component;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.Page;

public class BasicComponentTests extends TestCase {
	public void testBasic() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("componentTest", "basic", ArrayUtils.EMPTY_STRING_ARRAY);
		page.assertRenders();
		page.assertContains("both from controller");
		page.assertContains("hello from controller");
		page.assertContains("both from component");
		page.assertContains("hello from component");
	}
}
