package org.opensails.sails.form;

import java.util.Collection;

public interface IValidationEntry {
	public Collection<IValidationFailure> getFailures();

	public Object getModel();

	public String getModelName();
}
