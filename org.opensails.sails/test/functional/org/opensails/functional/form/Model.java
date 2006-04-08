package org.opensails.functional.form;

import java.util.Collection;

import org.opensails.sails.persist.AbstractIdentifiable;
import org.opensails.sails.util.Quick;
import org.opensails.sails.validation.constraints.Length;

public class Model extends AbstractIdentifiable {
	protected String hiddenProperty = "hiddenValue";
	protected String passwordProperty = "passwordValue";
	protected String textProperty = "textValue";
	protected String textareaProperty = "textareaValue";
	protected boolean checkboxProperty = false;
	protected Collection<String> checkboxListProperty = Quick.list("two");
	protected String radioProperty = "one";
	protected String selectProperty = "selectValue";

	@Length(min = 3, message = "Custom message should be used for length")
	protected String lengthValidated;

	protected SubModel subModel = new SubModel();

	public static class SubModel {
		protected String textProperty = "textValue";
	}
}