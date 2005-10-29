package org.opensails.rigging;

public class ShamDisposable implements Disposable {
	public boolean disposed;

	public void dispose() {
		disposed = true;
	}
}
