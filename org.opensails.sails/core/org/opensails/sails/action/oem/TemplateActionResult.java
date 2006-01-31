package org.opensails.sails.action.oem;

import java.util.regex.*;

import org.opensails.sails.event.*;
import org.opensails.sails.http.*;
import org.opensails.viento.*;

public class TemplateActionResult extends AbstractActionResult {
	public static final Pattern CONTROLLER_ACTION_PATTERN = Pattern.compile("^(.*?)/(.*?)?");
	
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

	public IBinding getBinding() {
		return getContainer().instance(IBinding.class);
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

	public void setLayout(String templateIdentifier) {
		layoutIdentifier = parseIdentifier(event, templateIdentifier);
	}

	/**
	 * @param templateIdentifier in the form of "action" or "controller/action"
	 */
	public void setTemplate(String templateIdentifier) {
		this.identifier = parseIdentifier(event, templateIdentifier);
	}

	@Override
	public String toString() {
		return "Template: <" + identifier + ">";
	}

	public void write(String string) {
		getEvent().write(string);
	}

	protected void initialize(ISailsEvent event, String identifier) {
		initializeHeaders(event);
		this.identifier = parseIdentifier(event, identifier);
	}

	protected void initializeHeaders(ISailsEvent event) {
		event.getResponse().setContentType(ContentType.TEXT_HTML.value());
	}

	protected String parseIdentifier(ISailsEvent event, String identifier) {
		if (identifier == null) return null;
		IEventProcessingContext processingContext = event.getContainer().instance(IEventProcessingContext.class);
		if (processingContext != null) return processingContext.getTemplatePath(identifier);
		
		if (CONTROLLER_ACTION_PATTERN.matcher(identifier).find()) return identifier;
		else return String.format("%s/%s", event.getProcessorName(), identifier);
	}
}
