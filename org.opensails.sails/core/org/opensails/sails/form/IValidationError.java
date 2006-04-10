package org.opensails.sails.form;

/**
 * IValidationError instances are attached to a ValidationContext when a model
 * object is validated through annotation processing or in actions during form
 * processing.
 * 
 * @author aiwilliams
 */
public interface IValidationError {

	/**
	 * @return the message that describes the error completely.
	 */
	String getFullMessage();

	/**
	 * @return the message that describes the error, not including any property
	 *         name.
	 */
	String getMessage();

}
