package org.opensails.sails.form;

public interface IFormValueModel {
	void expose(String name, Object model);

	Object value(String propertyPath);
}
