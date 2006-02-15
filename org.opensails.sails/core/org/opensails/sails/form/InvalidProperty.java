package org.opensails.sails.form;

import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.model.PropertyPathException;
import org.opensails.sails.validation.IInvalidProperty;

public class InvalidProperty implements IInvalidProperty {
	protected final String fieldName;
	protected final Object model;
	protected final IPropertyPath path;
	protected final PropertyPathException propertyPathException;

	public InvalidProperty(String fieldName, IPropertyPath path, Object model, PropertyPathException e) {
		this.fieldName = fieldName;
		this.path = path;
		this.model = model;
		this.propertyPathException = e;
	}

	public String getProperty() {
		return path.getProperty();
	}

	@Override
	public String toString() {
		return String.format("Invalid property path of [%s] on model [%s] for field %s:\n%s", path, model, fieldName, propertyPathException);
	}
}
