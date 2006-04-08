package org.opensails.sails.validation.oem;

import java.lang.reflect.Field;

import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidator;

public class FieldPropertyValidator implements IPropertyValidator {
	private final Class annotatedClass;
	private final Field annotatedField;
	private final IValidator validatorInstance;

	public FieldPropertyValidator(Class annotatedClass, Field annotatedField, IValidator validatorInstance) {
		this.annotatedClass = annotatedClass;
		this.annotatedField = annotatedField;
		this.validatorInstance = validatorInstance;
		this.annotatedField.setAccessible(true);
	}

	public IInvalidProperty validate(Object model) throws Exception {
		Object value = annotatedField.get(model);
		if (validatorInstance.validate(value)) return null;
		return new InvalidFieldProperty(annotatedClass, annotatedField, validatorInstance);
	}
}
