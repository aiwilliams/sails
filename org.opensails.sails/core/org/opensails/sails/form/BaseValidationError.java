package org.opensails.sails.form;

public class BaseValidationError implements IValidationError {

	protected String modelName;
	protected Object model;
	protected String errorMessage;

	public BaseValidationError(String modelName, Object model, String errorMessage) {
		this.modelName = modelName;
		this.model = model;
		this.errorMessage = errorMessage;
	}

	public String getFullMessage() {
		return errorMessage;
	}

	public String getMessage() {
		return errorMessage;
	}

}
