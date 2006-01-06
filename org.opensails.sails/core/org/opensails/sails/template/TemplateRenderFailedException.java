package org.opensails.sails.template;

import org.opensails.sails.*;

public class TemplateRenderFailedException extends SailsException {
	public TemplateRenderFailedException(String templateIdentifier, Throwable cause) {
		super(String.format("Failed rendering template [%s]", templateIdentifier), cause);
	}
}
