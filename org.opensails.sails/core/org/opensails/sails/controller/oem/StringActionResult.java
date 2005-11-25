package org.opensails.sails.controller.oem;

import org.opensails.sails.ISailsEvent;

public class StringActionResult extends AbstractActionResult {
	protected String content;

	public StringActionResult(ISailsEvent event, Object content) {
		super(event);
		this.content = content != null ? content.toString() : "";
	}

	public String getContent() {
		return content;
	}
}
