package org.opensails.rigging;


public class ShamStoppable implements Stoppable {
	public boolean stopped;
	
	public void stop() {
		stopped = true;
	}
}
