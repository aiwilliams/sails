package org.opensails.sails.form;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.html.HtmlGenerator;
import org.opensails.text.Inflector;
import org.opensails.viento.IRenderable;

/**
 * A mapping of IValidationError instances for a particular model instance.
 * 
 * @author aiwilliams
 */
public class ValidationErrors implements IRenderable {
	/**
	 * Returned to view when there are no error for a model
	 */
	public static final ValidationErrors NULL = new ValidationErrors("NULL", null) {
		@Override
		public String renderThyself() {
			return StringUtils.EMPTY;
		}
	};

	protected String modelName;
	protected Object model;
	protected List<IValidationError> errors;

	public ValidationErrors(String modelName, Object model) {
		this.modelName = modelName;
		this.model = model;
		this.errors = new ArrayList<IValidationError>();
	}

	/**
	 * Adds a PropertyError for the named property using the provided
	 * errorMessage.
	 * <p>
	 * These values will be used to form a complete sentence.
	 * 
	 * @param property
	 * @param errorMessage
	 */
	public void add(String property, String errorMessage) {
		errors.add(new PropertyError(modelName, model, property, errorMessage));
	}

	/**
	 * Adds a BaseValidationError using the provided errorMessage. This should
	 * be complete sentence.
	 * 
	 * @param errorMessage
	 */
	public void addToBase(String errorMessage) {
		errors.add(new BaseValidationError(modelName, model, errorMessage));
	}

	/**
	 * @return a List of errors, in the order they were added
	 */
	public List<IValidationError> getErrors() {
		return errors;
	}

	public Object getModel() {
		return model;
	}

	public String getModelName() {
		return modelName;
	}

	public boolean isEmpty() {
		return errors.isEmpty();
	}

	public String renderThyself() {
		StringWriter output = new StringWriter();
		HtmlGenerator html = new HtmlGenerator(output);

		try {
			html.beginTag("h2").write(Inflector.pluralize(errors.size(), "error")).write(" prohibited this ").write(modelName).write(" from being saved.").endTag("h2");
			html.beginTag("p").write("There were problems with the following fields:").endTag("p");
			html.beginTag("ul");
			for (IValidationError error : getErrors()) {
				html.beginTag("li");
				html.write(error.getFullMessage());
				html.endTag("li");
			}
			html.endTag("ul");
		} catch (Exception e) {
			throw new SailsException("Failure rendering validation errors", e);
		}
		return output.toString();
	}

	@Override
	public String toString() {
		return renderThyself();
	}
}
