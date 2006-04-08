package org.opensails.sails.form;

import org.opensails.sails.model.PropertyAccessException;
import org.opensails.sails.util.BleedingEdgeException;
import org.opensails.sails.validation.IInvalidProperty;

public class InaccessibleProperty implements IInvalidProperty {
	protected final PropertyAccessException accessorException;
	protected final String fieldName;

	public InaccessibleProperty(String fieldName, PropertyAccessException e) {
		this.fieldName = fieldName;
		this.accessorException = e;
	}

	public String getMessage() {
		throw new BleedingEdgeException("implement");
	}

	public String getProperty() {
		return fieldName;
	}

	@Override
	public String toString() {
		return String.format("Inaccessible field [%s]:\n%s", fieldName, accessorException);
	}
}
