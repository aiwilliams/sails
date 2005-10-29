package org.opensails.sails.controller.oem;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.template.ITemplateBinding;

public class TemplateActionResult implements IActionResult {
	protected final ISailsEvent event;
	protected String identifier;

	public TemplateActionResult(ISailsEvent event) {
		this.event = event;
		initialize(event, event.getActionName());
	}

	public TemplateActionResult(ISailsEvent event, String identifier) {
		this.event = event;
		initialize(event, identifier);
	}

	public ITemplateBinding getBinding() {
		return getContainer().instance(ITemplateBinding.class);
	}

	public ScopedContainer getContainer() {
		return getEvent().getContainer();
	}

	public IController getController() {
		return getEvent().getContainer().instance(IController.class);
	}

	public ISailsEvent getEvent() {
		return event;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void write(String string) {
		getEvent().write(string);
	}

	@Override
	public String toString() {
		return "Template: <" + identifier + ">";
	}
	
	protected void initialize(ISailsEvent event, String identifier) {
		initializeContentType(event);
		initializeIdentifier(event, identifier);
	}

	protected void initializeContentType(ISailsEvent event) {
		event.getResponse().setContentType(ContentType.TEXT_HTML.toHttpValue());
	}

	protected void initializeIdentifier(ISailsEvent event, String identifier) {
		this.identifier = String.format("%s/%s", event.getControllerName(), identifier);
	}
}
