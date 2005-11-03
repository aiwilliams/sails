package org.opensails.sails.processors;

import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.controller.oem.StringActionResult;

public class StringActionResultProcessor implements IActionResultProcessor<StringActionResult> {
	public void process(StringActionResult result) {
		result.getEvent().write(result.getContent());
	}
}
