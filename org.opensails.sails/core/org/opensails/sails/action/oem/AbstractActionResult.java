package org.opensails.sails.action.oem;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;

public abstract class AbstractActionResult implements IActionResult {
	protected final ISailsEvent event;

	public AbstractActionResult(ISailsEvent event) {
		this.event = event;
	}

	public ScopedContainer getContainer() {
		return event.getContainer();
	}

	public IControllerImpl getController() {
		return event.getContainer().instance(IControllerImpl.class);
	}

	public ISailsEvent getEvent() {
		return event;
	}
}
