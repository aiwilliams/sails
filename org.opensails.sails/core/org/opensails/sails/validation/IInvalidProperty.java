package org.opensails.sails.validation;

public interface IInvalidProperty {
	String getMessage();

	/**
	 * @return the name of the property on the target
	 */
	String getProperty();
}
