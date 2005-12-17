package org.opensails.sails.tester.oem;

import org.opensails.sails.SailsException;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.event.oem.ExceptionEvent;

public class ErrorController extends Controller {
    public ErrorController() {
        super(null, null);
    }

    @Override
    public IActionResult process(ExceptionEvent event) {
        throw new SailsException("An exception occurred while processing the action <" + event.getOriginatingEvent().getActionName() + ">", event.getException());
    }
}
