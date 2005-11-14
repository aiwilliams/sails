package org.opensails.sails.oem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.oem.Controller;

public class GetEvent extends AbstractEvent {
	public GetEvent(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	public GetEvent(ISailsApplication application, HttpServletRequest req, HttpServletResponse resp) {
		super(application, application.getContainer(), req, resp);
	}

	public IActionResult visit(Controller controller) {
		return controller.process(this);
	}
}
