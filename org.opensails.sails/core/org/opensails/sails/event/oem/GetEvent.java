package org.opensails.sails.event.oem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.action.IActionResult;

public class GetEvent extends AbstractEvent {
	public GetEvent(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	public GetEvent(ISailsApplication application, HttpServletRequest req, HttpServletResponse resp) {
		super(application, application.getContainer(), req, resp);
	}

	@Override
	public IActionResult visit(IActionEventProcessor eventProcessor) {
		return eventProcessor.process(this);
	}
}
