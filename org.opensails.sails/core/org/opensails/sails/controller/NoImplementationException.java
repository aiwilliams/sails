package org.opensails.sails.controller;

import org.opensails.sails.SailsException;

public class NoImplementationException extends SailsException {
	public NoImplementationException(IController controller) {
		super("There is no class implementing behavior for " + controller);
	}
}
