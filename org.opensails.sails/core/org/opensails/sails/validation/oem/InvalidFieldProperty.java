package org.opensails.sails.validation.oem;

import java.lang.reflect.Field;

import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidator;

public class InvalidFieldProperty implements IInvalidProperty {
	protected Class annotatedClass;
	protected Field annotatedField;
	protected IValidator validator;

	public InvalidFieldProperty(Class annotatedClass, Field annotatedField, IValidator validator) {
		this.annotatedClass = annotatedClass;
		this.annotatedField = annotatedField;
		this.validator = validator;
	}

	public String getMessage() {
		return validator.getConstraintMessage();
	}

	public String getProperty() {
		return annotatedField.getName();
	}

}
