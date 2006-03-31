package org.opensails.sails.model;

/**
 * Thrown by an IPropertyAccessor when it cannot answer for an IPropertyPath on
 * a model.
 * <p>
 * If the property exists, but could not be accessed, and
 * PropertyAccessException is thrown.
 * 
 * @author aiwilliams
 */
public class UnknownPropertyException extends PropertyAccessException {
	private static final long serialVersionUID = -1233777024606425187L;

	public UnknownPropertyException(String property, Object model, String message) {
		super(property, model, message);
	}

}
