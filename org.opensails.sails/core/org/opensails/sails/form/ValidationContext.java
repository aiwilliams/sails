package org.opensails.sails.form;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.sails.SailsException;
import org.opensails.sails.html.HtmlGenerator;
import org.opensails.sails.model.ModelContext;
import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidationEngine;
import org.opensails.sails.validation.IValidationResult;
import org.opensails.viento.IRenderable;

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
public class ValidationContext implements IRenderable {
	protected final Map<String, ValidationErrors> errors;
	protected final ModelContext modelContext;
	protected final IValidationEngine validationEngine;

	public ValidationContext(ModelContext modelContext, IValidationEngine validationEngine) {
		this.modelContext = modelContext;
		this.validationEngine = validationEngine;
		errors = new HashMap<String, ValidationErrors>();
	}

	/**
	 * @return all IValidationError instances from all ValidationErrors, grouped
	 *         by model.
	 */
	public List<IValidationError> allErrors() {
		if (errors.isEmpty()) return Collections.emptyList();

		List<IValidationError> all = new ArrayList<IValidationError>();
		for (ValidationErrors errorsForModel : errors.values())
			all.addAll(errorsForModel.getErrors());
		return all;
	}

	public ValidationErrors errorsFor(String modelName) {
		ValidationErrors validationErrors = errors.get(modelName);
		if (validationErrors == null) {
			Object model = modelContext.getModel(modelName);
			validationErrors = new ValidationErrors(modelName, model);
			errors.put(modelName, validationErrors);
		}
		return validationErrors;
	}

	public boolean hasErrors() {
		return !allErrors().isEmpty();
	}

	public String renderThyself() {
		if (!hasErrors()) return "";

		StringWriter output = new StringWriter();
		HtmlGenerator html = new HtmlGenerator(output);
		try {
			html.beginTag("div").idAttribute("errorExplanation").classAttribute("errorExplanation");
			for (ValidationErrors error : errors.values())
				html.write(error.renderThyself());
			html.endTag("div");
		} catch (Exception e) {
			throw new SailsException("Failure rendering validation errors", e);
		}
		return output.toString();
	}

	@Override
	public String toString() {
		return renderThyself();
	}

	public boolean validate() {
		for (Map.Entry<String, Object> modelEntry : modelContext.getModelEntries()) {
			IValidationResult validationResult = validationEngine.validate(modelEntry.getValue());
			for (IInvalidProperty invalidProperty : validationResult.getInvalidProperties())
				errorsFor(modelEntry.getKey()).add(invalidProperty.getProperty(), invalidProperty.getMessage());
		}
		return !hasErrors();
	}
}
