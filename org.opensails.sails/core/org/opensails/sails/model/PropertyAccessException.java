package org.opensails.sails.model;

import org.opensails.sails.SailsException;

/**
 * Thrown by an IPropertyAccessor when accessing a property on a model fails.
 * <p>
 * If the property does not exist, an UnknownPropertyException will occur.
 * 
 * @author aiwilliams
 */
public class PropertyAccessException extends SailsException {
	private static final long serialVersionUID = 4658618778510160855L;
	protected final Object model;
	protected final String property;

	public PropertyAccessException(String property, Object model, String message) {
		this(property, model, message, null);
	}

	public PropertyAccessException(String property, Object model, String message, Throwable cause) {
		super(message, cause);
		this.property = property;
		this.model = model;
	}

	public String getProperty() {
		return property;
	}
}
