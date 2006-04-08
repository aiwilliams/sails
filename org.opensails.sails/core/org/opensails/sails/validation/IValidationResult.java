package org.opensails.sails.validation;

public interface IValidationResult {
	IInvalidProperty[] EMPTY_INVALID_PROPERTY_ARRAY = new IInvalidProperty[0];

	IInvalidProperty[] getInvalidProperties();

	boolean isValid();
}
