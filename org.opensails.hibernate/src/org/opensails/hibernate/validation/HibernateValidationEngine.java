package org.opensails.hibernate.validation;

import org.hibernate.validator.ClassValidator;
import org.opensails.sails.validation.IValidationEngine;
import org.opensails.sails.validation.IValidationResult;

public class HibernateValidationEngine implements IValidationEngine {
	public IValidationResult validate(Object model) {
		return new HibernateValidationResult(model, new ClassValidator(model.getClass()));
	}
}
