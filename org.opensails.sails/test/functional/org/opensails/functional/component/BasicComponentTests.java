package org.opensails.functional.component;

import junit.framework.*;

import org.apache.commons.lang.*;
import org.opensails.functional.*;
import org.opensails.functional.components.*;
import org.opensails.sails.tester.*;

public class BasicComponentTests extends TestCase {
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
}
