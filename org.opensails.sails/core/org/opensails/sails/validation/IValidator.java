package org.opensails.sails.validation;

import java.lang.annotation.Annotation;

public interface IValidator<A extends Annotation> {
	/**
	 * @return an Annotation-instance-specific message indicating that which
	 *         constitutes a valid value
	 */
	String getConstraintMessage();

	void init(A constraint);

	boolean validate(Object value) throws Exception;
}
