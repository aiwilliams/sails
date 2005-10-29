package org.opensails.sails.model.oem;

import org.opensails.sails.model.IModelContext;
import org.opensails.sails.model.IPropertyPath;

/**
 * Always returns the same model instance, never considering the path.
 * 
 * This is useful when you are binding a form to one model - all the properties
 * on the form are relative to this one model.
 */
public class SingleModelContext implements IModelContext {
	protected final Object model;

	public SingleModelContext(Object model) {
		this.model = model;
	}

	public Object getModel(IPropertyPath path) {
		return model;
	}
}
