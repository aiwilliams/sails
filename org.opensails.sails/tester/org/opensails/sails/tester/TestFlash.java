package org.opensails.sails.tester;

import junit.framework.Assert;

import org.opensails.sails.oem.Flash;

public class TestFlash {
	protected final Flash flash;

	public TestFlash(Flash flash) {
		this.flash = flash;
	}

	public void assertContains(Object key, Object value) {
		Assert.assertEquals(String.format("Expected flash to contain %s", key), value, flash.get(key));
	}
}
