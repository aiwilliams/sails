package org.opensails.sails.form;

import org.opensails.sails.model.UnknownPropertyException;
import org.opensails.sails.util.BleedingEdgeException;
import org.opensails.sails.validation.IInvalidProperty;

public class InvalidProperty implements IInvalidProperty {
	protected final String fieldName;
	protected final UnknownPropertyException propertyPathException;

	public InvalidProperty(String fieldName, UnknownPropertyException e) {
		this.fieldName = fieldName;
		this.propertyPathException = e;
	}

	public String getMessage() {
		throw new BleedingEdgeException("implement");
	}

	public String getProperty() {
		return fieldName;
	}

	@Override
	public String toString() {
		return String.format("Invalid field [%s]:\n%s", fieldName, propertyPathException);
	}
}
