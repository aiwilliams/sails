package org.opensails.functional;

import junit.framework.Assert;

import org.opensails.rigging.Startable;

public class ShamApplicationStartable implements Startable {
	private boolean wasStarted;

	public void assertStarted() {
		Assert.assertTrue("Expect application startables to be started", wasStarted);
	}

	public void start() {
		wasStarted = true;
	}
}
