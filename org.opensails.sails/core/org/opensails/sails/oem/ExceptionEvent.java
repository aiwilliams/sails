package org.opensails.sails.oem;

import javax.servlet.http.HttpServletResponse;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.oem.Controller;

public class ExceptionEvent extends AbstractEvent {
	public static final String CONTROLLER_NAME = "error";

	protected final Throwable exception;
	protected ISailsEvent originatingEvent;

	public ExceptionEvent(ISailsEvent event, Throwable t) {
		super(event.getApplication(), event.getRequest(), event.getResponse());
		this.originatingEvent = event;
		this.exception = t;
		setContainer(event.getContainer());
		getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	@Override
	public void beginDispatch() {}

	@Override
	public void endDispatch() {}

	public Throwable getException() {
		return exception;
	}

	public ISailsEvent getOriginatingEvent() {
		return originatingEvent;
	}

	@Override
	public IActionResult visit(Controller controller) {
		return controller.process(this);
	}

	@Override
	protected void containerSet() {
		container.register(this);
	}

	@Override
	protected void initialize() {
		super.initialize();
		url.setAction("exception");
		url.setController("error");
	}
}
