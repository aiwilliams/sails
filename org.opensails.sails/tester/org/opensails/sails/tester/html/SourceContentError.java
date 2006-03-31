package org.opensails.sails.tester.html;

import junit.framework.AssertionFailedError;

public class SourceContentError extends AssertionFailedError {
	private static final long serialVersionUID = -2931474468751413801L;

	protected String containerSource;

	public SourceContentError(String containerSource, String message) {
		super(String.format("%s with container content of\n%s", message, containerSource));
		this.containerSource = containerSource;
	}
}
