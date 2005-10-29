package org.opensails.sails.model.oem;

import org.opensails.sails.model.AccessorException;

public class UnknownPropertyException extends AccessorException {
	public UnknownPropertyException(Class targetType, String propertyName) {
		super(propertyName, targetType, "Unknown property");
	}
}
