package org.opensails.sails.form;

import java.util.Collection;

public interface IValidationErrors {
	public Collection<IValidationFailure> getFailures();

	public Object getModel();

	public String getModelName();
}
