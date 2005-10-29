package org.opensails.sails.model;

public class AccessorException extends RuntimeException {
	private static final long serialVersionUID = 4658618778510160855L;
	protected final Object model;
	protected final String property;

	public AccessorException(String property, Object model, String message) {
		this(property, model, message, null);
	}

	public AccessorException(String property, Object model, String message, Throwable cause) {
		super(message, cause);
		this.property = property;
		this.model = model;
	}

	public String getProperty() {
		return property;
	}
}
