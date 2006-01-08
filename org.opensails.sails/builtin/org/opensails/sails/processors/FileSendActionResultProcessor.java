package org.opensails.sails.processors;

import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.oem.FileSendActionResult;

public class FileSendActionResultProcessor implements IActionResultProcessor<FileSendActionResult> {
	public void process(FileSendActionResult result) {
		result.write();
	}
}
