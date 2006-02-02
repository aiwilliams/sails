package org.opensails.sails.tester.oem;

import org.opensails.sails.*;
import org.opensails.sails.action.*;
import org.opensails.sails.controller.*;
import org.opensails.sails.controller.oem.*;
import org.opensails.sails.event.oem.*;

public class ErrorController<I extends IControllerImpl> extends Controller<I> {
	public ErrorController() {
		super(null, null);
	}

	@Override
	public IActionResult process(ExceptionEvent event) {
		throw new SailsException("An exception occurred while processing the action <" + event.getOriginatingEvent().getActionName() + ">", event.getException());
	}
}
