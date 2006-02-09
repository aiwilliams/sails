package org.opensails.sails.template;

import org.opensails.sails.*;

public class TemplateRenderFailedException extends SailsException {

	private static final long serialVersionUID = 126612102044227223L;

	public TemplateRenderFailedException(String templateIdentifier, Throwable cause) {
		super(String.format("Failed rendering template [%s]", templateIdentifier), cause);
	}

	public TemplateRenderFailedException(String message) {
		super(message);
	}

	public TemplateRenderFailedException(Throwable cause) {
		super(cause);
	}
}
