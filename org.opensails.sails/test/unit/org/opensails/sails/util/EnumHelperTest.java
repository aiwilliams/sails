package org.opensails.sails.util;

import junit.framework.TestCase;

public class EnumHelperTest extends TestCase {
	public void testTitleCase() throws Exception {
		assertEquals("One", EnumHelper.titleCase(Test.ONE));
		assertEquals("Two Three", EnumHelper.titleCase(Test.TWO_THREE));
	}

	enum Test {
		ONE, TWO_THREE
	}
}
