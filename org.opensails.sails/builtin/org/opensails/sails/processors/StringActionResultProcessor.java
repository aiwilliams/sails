package org.opensails.sails.processors;

import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.oem.StringActionResult;

public class StringActionResultProcessor implements IActionResultProcessor<StringActionResult> {
	public void process(StringActionResult result) {
		result.getEvent().write(result.getContent());
	}
}
