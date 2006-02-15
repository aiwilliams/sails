package org.opensails.sails.form;

import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.validation.IInvalidProperty;

public class UnadaptableProperty implements IInvalidProperty {
	protected final AdaptationException adaptationException;
	protected final String fieldName;
	protected final Object model;
	protected final IPropertyPath path;

	public UnadaptableProperty(String fieldName, IPropertyPath path, Object model, AdaptationException e) {
		this.fieldName = fieldName;
		this.path = path;
		this.model = model;
		this.adaptationException = e;
	}

	public String getProperty() {
		return path.getProperty();
	}

	@Override
	public String toString() {
		return String.format("Unable to adapt field named %s for use on model [%s] path of [%s]:\n%s", fieldName, model, path, adaptationException);
	}
}
