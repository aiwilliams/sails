package org.opensails.js;

import org.opensails.js.JavascriptArray;
import org.opensails.sails.ApplicationScope;

import junit.framework.TestCase;

public class JavascriptArrayTest extends TestCase {

	public void testIt() throws Exception {
		JavascriptArray array = new JavascriptArray();
		array.add("asdf");
		array.add(1.2);
		array.add(ApplicationScope.COMPONENT);
		assertEquals("['asdf',1.2,'COMPONENT']", array.renderThyself());
	}
}
