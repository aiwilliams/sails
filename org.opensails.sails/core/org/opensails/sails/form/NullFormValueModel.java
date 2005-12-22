package org.opensails.sails.form;

public class NullFormValueModel implements IFormValueModel {
	public void expose(String name, Object model) {
		throw new UnsupportedOperationException("Attempt to expose model to NullFormValueModel seems like a bug");
	}

	public Object value(String propertyPath) {
		return null;
	}
}
