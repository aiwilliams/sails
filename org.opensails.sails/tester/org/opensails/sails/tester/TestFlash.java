package org.opensails.sails.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.opensails.sails.oem.Flash;

public class TestFlash {
	protected final Flash flash;

	public TestFlash(Flash flash) {
		this.flash = flash;
	}

	public void assertContains(Object key) {
		assertNotNull(flash.get(key));
	}

	public void assertContains(Object key, Object value) {
		assertEquals(String.format("Expected flash to contain %s", key), value, flash.get(key));
	}
}
