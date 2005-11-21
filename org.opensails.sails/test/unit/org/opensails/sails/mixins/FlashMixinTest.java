package org.opensails.sails.mixins;

import junit.framework.TestCase;

import org.opensails.sails.oem.Flash;

public class FlashMixinTest extends TestCase {
	public void testIt() throws Exception {
		Flash flash = new Flash();
		flash.put("a", "aa");

		FlashMixin mixin = new FlashMixin(flash);
		assertNull(mixin.invoke());
		assertNull(mixin.invoke(new Object[] {}));
		assertEquals("aa", mixin.invoke(new Object[] { "a" }));
	}
}
