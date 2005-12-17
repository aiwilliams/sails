package org.opensails.sails.action.oem;

import org.opensails.sails.event.ISailsEvent;

public class PartialActionResult extends TemplateActionResult {
	public PartialActionResult(ISailsEvent event) {
		super(event);
	}

	public PartialActionResult(ISailsEvent event, String partialIdentifier) {
		super(event, partialIdentifier);
	}

	@Override
	protected void initializeIdentifier(ISailsEvent event, String identifier) {
		this.identifier = String.format("%s/_%s", event.getProcessorName(), identifier);
	}
}
