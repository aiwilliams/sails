package org.opensails.sails.controller.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.template.ITemplateBinding;

public class TemplateActionResult extends AbstractActionResult {
	protected String identifier;
	protected String layoutIdentifier;

	public TemplateActionResult(ISailsEvent event) {
		super(event);
		initialize(event, event.getActionName());
	}

	public TemplateActionResult(ISailsEvent event, String identifier) {
		super(event);
		initialize(event, identifier);
	}

	public ITemplateBinding getBinding() {
		return getContainer().instance(ITemplateBinding.class);
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getLayout() {
		return layoutIdentifier;
	}

	public boolean hasLayout() {
		return layoutIdentifier != null;
	}

	public void layout(String templateIdentifier) {
		layoutIdentifier = templateIdentifier;
	}

	@Override
	public String toString() {
		return "Template: <" + identifier + ">";
	}

	public void write(String string) {
		getEvent().write(string);
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
