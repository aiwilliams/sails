package org.opensails.sails.controller.oem;

import org.opensails.sails.ISailsEvent;

public class StringActionResult extends AbstractActionResult {
	protected String content;

	public StringActionResult(ISailsEvent event, String content) {
		super(event);
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
