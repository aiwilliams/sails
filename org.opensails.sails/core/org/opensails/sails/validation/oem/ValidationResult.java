package org.opensails.sails.validation.oem;

import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidationResult;

public class ValidationResult implements IValidationResult {
	private final Object model;
	private final ModelValidator validator;
	private IInvalidProperty[] invalids;

	public ValidationResult(Object model, ModelValidator validator) {
		this.model = model;
		this.validator = validator;
	}

	public boolean isValid() {
		return getInvalidProperties().length == 0;
	}

	public IInvalidProperty[] getInvalidProperties() {
		if (invalids == null)
			invalids = validator.invalidProperties(model);
		return invalids;
	}
    
    @Override
    public String toString() {
        return isValid() ? "valid" : "invalid";
    }
}
