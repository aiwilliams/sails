package org.opensails.sails.processors;

import java.io.IOException;

import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.SailsException;
import org.opensails.sails.controller.oem.RedirectActionResult;

public class RedirectActionResultProcessor implements IActionResultProcessor<RedirectActionResult> {
	public void process(RedirectActionResult result) {
		try {
			result.getEvent().getResponse().sendRedirect(result.getRedirectUrl().render());
		} catch (IOException e) {
			throw new SailsException("Failure redirecting client", e);
		}
	}
}
