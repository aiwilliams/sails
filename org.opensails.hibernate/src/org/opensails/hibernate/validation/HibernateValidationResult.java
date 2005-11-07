package org.opensails.hibernate.validation;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidationResult;

public class HibernateValidationResult implements IValidationResult {
	private final ClassValidator validator;
	private final Object model;
	private IInvalidProperty[] invalids;

	public HibernateValidationResult(Object model, ClassValidator validator) {
		this.model = model;
		this.validator = validator;
	}

	public boolean isValid() {
		return getInvalidProperties().length == 0;
	}

	public IInvalidProperty[] getInvalidProperties() {
		if (invalids == null) {
			InvalidValue[] invalidValues = validator.getInvalidValues(model);
			if (invalidValues.length == 0)
				invalids = EMPTY_INVALID_PROPERTY_ARRAY;
			else {
				invalids = new IInvalidProperty[invalidValues.length];
				for (int i = 0; i < invalidValues.length; i++)
					invalids[i] = new HibernateInvalidProperty(invalidValues[i]);
			}
		}
		return invalids;
	}
}
