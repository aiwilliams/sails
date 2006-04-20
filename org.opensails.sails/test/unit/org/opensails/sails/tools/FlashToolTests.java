package org.opensails.sails.tools;

import junit.framework.TestCase;

import org.opensails.sails.oem.Flash;
import org.opensails.sails.tools.FlashTool;

public class FlashToolTests extends TestCase {
	public void testIt() throws Exception {
		Flash flash = new Flash();
		flash.put("a", "aa");

		FlashTool mixin = new FlashTool(flash);
		assertEquals("", mixin.invoke());
		assertEquals("", mixin.invoke(new Object[] {}));
		assertEquals("aa", mixin.invoke(new Object[] { "a" }));
		assertEquals("", mixin.invoke(new Object[] {"b"}));
	}
}
