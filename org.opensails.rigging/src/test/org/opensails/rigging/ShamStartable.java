package org.opensails.rigging;


public class ShamStartable implements Startable {
	public boolean started;

	public void start() {
		started = true;
	}
}
