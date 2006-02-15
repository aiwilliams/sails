package org.opensails.sails.form;

import org.opensails.sails.model.AccessorException;
import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.validation.IInvalidProperty;

public class InaccessibleProperty implements IInvalidProperty {
	protected final AccessorException accessorException;
	protected final String fieldName;
	protected final Object model;
	protected final IPropertyPath path;

	public InaccessibleProperty(String fieldName, IPropertyPath path, Object model, AccessorException e) {
		this.fieldName = fieldName;
		this.path = path;
		this.model = model;
		this.accessorException = e;
	}

	public String getProperty() {
		return path.getProperty();
	}

	@Override
	public String toString() {
		return String.format("Inaccessible property path of [%s] on model [%s] for field %s:\n%s", path, model, fieldName, accessorException);
	}
}
