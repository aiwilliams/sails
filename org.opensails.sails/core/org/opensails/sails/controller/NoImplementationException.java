package org.opensails.sails.controller;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.oem.IActionEventProcessor;

public class NoImplementationException extends SailsException {
	public NoImplementationException(IActionEventProcessor controller) {
		super("There is no class implementing behavior for " + controller);
	}
}
