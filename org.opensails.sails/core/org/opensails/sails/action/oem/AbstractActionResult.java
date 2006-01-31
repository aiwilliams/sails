package org.opensails.sails.action.oem;

import org.opensails.rigging.*;
import org.opensails.sails.action.*;
import org.opensails.sails.event.*;
import org.opensails.sails.http.*;

public abstract class AbstractActionResult implements IActionResult {
	protected final ISailsEvent event;

	public AbstractActionResult(ISailsEvent event) {
		this.event = event;
	}

	public ScopedContainer getContainer() {
		return event.getContainer();
	}

	public IEventProcessingContext getProcessingContext() {
		return event.getContainer().instance(IEventProcessingContext.class);
	}

	public ISailsEvent getEvent() {
		return event;
	}

	public void setHeader(HttpHeader header) {
		event.getResponse().setHeader(header.name(), header.value());
	}
}
