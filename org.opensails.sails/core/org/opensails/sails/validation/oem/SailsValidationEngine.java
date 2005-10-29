package org.opensails.sails.validation.oem;

import org.opensails.sails.validation.IValidationEngine;
import org.opensails.sails.validation.IValidationResult;

public class SailsValidationEngine implements IValidationEngine {
    public IValidationResult validate(Object model) {
    	return new ValidationResult(model, new ModelValidator(model.getClass()));
    }
}
