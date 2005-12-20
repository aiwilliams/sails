package org.opensails.sails.processors;

import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.oem.StringActionResult;
import org.opensails.sails.event.ISailsEvent;

public class StringActionResultProcessor implements IActionResultProcessor<StringActionResult> {
	public void process(StringActionResult result) {
		ISailsEvent event = result.getEvent();
		String content = result.getContent();
		event.getResponse().setContentLength(content.length());
		event.write(content);
	}
}
