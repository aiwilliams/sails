package org.opensails.sails.controller;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.oem.IActionEventProcessor;

public class NoImplementationException extends SailsException {
	private static final long serialVersionUID = 6175465255711371676L;

	public NoImplementationException(IActionEventProcessor controller) {
		super("There is no class implementing behavior for " + controller);
	}

	public NoImplementationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoImplementationException(String message) {
		super(message);
	}

	public NoImplementationException(Throwable cause) {
		super(cause);
	}
}
