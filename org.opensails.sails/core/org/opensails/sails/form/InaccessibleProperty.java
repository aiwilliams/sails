package org.opensails.sails.form;

import org.opensails.sails.model.AccessorException;
import org.opensails.sails.validation.IInvalidProperty;

public class InaccessibleProperty implements IInvalidProperty {
	protected final AccessorException accessorException;

	public InaccessibleProperty(AccessorException e) {
		this.accessorException = e;
	}

	public String getProperty() {
		return accessorException.getProperty();
	}

	@Override
	public String toString() {
		return accessorException.getMessage();
	}
}
