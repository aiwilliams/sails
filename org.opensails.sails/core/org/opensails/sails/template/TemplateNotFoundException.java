package org.opensails.sails.template;

import org.opensails.sails.SailsException;

public class TemplateNotFoundException extends SailsException {
    protected final ITemplateBinding binding;

    public TemplateNotFoundException(String templateIdentifier, ITemplateBinding binding) {
        super(String.format("Could not locate the template identified as %s", templateIdentifier));
        this.binding = binding;
    }
}
