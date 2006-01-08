package org.opensails.sails.action.oem;

import java.util.regex.Pattern;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.http.ContentType;
import org.opensails.viento.IBinding;

public class TemplateActionResult extends AbstractActionResult {
	static final Pattern CONTROLLER_ACTION_PATTERN = Pattern.compile("^(.*?)/(.*?)?");

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
		layoutIdentifier = templateIdentifier;
	}

	public void setTemplate(String templateIdentifier) {
		this.identifier = templateIdentifier;
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
		if (CONTROLLER_ACTION_PATTERN.matcher(identifier).find()) setTemplate(identifier);
		setTemplate(String.format("%s/%s", event.getProcessorName(), identifier));
	}
}
