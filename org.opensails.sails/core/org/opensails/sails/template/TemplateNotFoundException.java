package org.opensails.sails.template;

import org.opensails.sails.SailsException;
import org.opensails.viento.IBinding;

public class TemplateNotFoundException extends SailsException {
	private static final long serialVersionUID = 8033220718032810726L;

	protected final IBinding binding;

	public TemplateNotFoundException(String templateIdentifier, IBinding binding) {
		super(String.format("Could not locate the template identified as %s", templateIdentifier));
		this.binding = binding;
	}
}
