package org.opensails.sails.processors;

import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.oem.InputStreamActionResult;

public class InputStreamActionResultProcessor implements IActionResultProcessor<InputStreamActionResult> {

	public void process(InputStreamActionResult result) {
		result.write();
	}

}
