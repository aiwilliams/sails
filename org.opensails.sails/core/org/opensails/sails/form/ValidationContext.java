package org.opensails.sails.form;

import java.util.ArrayList;
import java.util.Collection;

import org.opensails.sails.model.ModelContext;
import org.opensails.sails.util.BleedingEdgeException;

/**
 * The context within which all validation information is maintained during form
 * processing.
 * <p>
 * This plays a role different than that of {@link ModelContext} in that it
 * understands more about the environment of an action invocation. It will be
 * bound to tools that provide additional form rendering and processing
 * capabilities that are beyond the task of reading and writing the models.
 * 
 * @author aiwilliams
 */
public class ValidationContext {
	protected Collection<IValidationFailure> failures;

	public ValidationContext() {
		failures = new ArrayList<IValidationFailure>();
	}

	public IValidationErrors getEntry(String modelName) {
		throw new BleedingEdgeException("implement");
	}

	public Collection<IValidationFailure> getErrors() {
		return failures;
	}

}
