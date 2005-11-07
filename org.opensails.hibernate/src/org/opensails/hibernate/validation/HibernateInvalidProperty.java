package org.opensails.hibernate.validation;

import org.hibernate.validator.InvalidValue;
import org.opensails.sails.validation.IInvalidProperty;

public class HibernateInvalidProperty implements IInvalidProperty {
	private final InvalidValue value;

	public HibernateInvalidProperty(InvalidValue value) {
		this.value = value;
	}

	public String getProperty() {
		return value.getPropertyName();
	}
}
