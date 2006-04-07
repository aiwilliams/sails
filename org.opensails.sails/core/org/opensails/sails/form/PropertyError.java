package org.opensails.sails.form;

public class PropertyError implements IValidationError {

	protected String modelName;
	protected Object model;
	protected String property;
	protected String errorMessage;

	public PropertyError(String modelName, Object model, String property, String errorMessage) {
		this.modelName = modelName;
		this.model = model;
		this.property = property;
		this.errorMessage = errorMessage;
	}

	public String getMessage() {
		return errorMessage;
	}

}
