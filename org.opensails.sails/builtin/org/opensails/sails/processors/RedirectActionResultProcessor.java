package org.opensails.sails.processors;

import java.io.IOException;

import org.opensails.sails.SailsException;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.oem.RedirectActionResult;

public class RedirectActionResultProcessor implements IActionResultProcessor<RedirectActionResult> {
	public void process(RedirectActionResult result) {
		try {
			result.getEvent().getResponse().sendRedirect(result.getRedirectUrl().renderThyself());
		} catch (IOException e) {
			throw new SailsException("Failure redirecting client", e);
		}
	}
}
