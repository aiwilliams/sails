package org.opensails.js;

import junit.framework.TestCase;

public class JavascriptObjectTest extends TestCase {
	
	public void testIt() throws Exception {
		JavascriptObject object = new JavascriptObject();
		object.set("one", "as'\ndf");
		object.set("two", 2);
		assertEquals("{one:'as\\'\\ndf',two:2}", object.renderThyself());
	}

}
