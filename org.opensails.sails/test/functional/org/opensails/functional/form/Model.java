package org.opensails.functional.form;

import java.util.Collection;

import org.opensails.sails.persist.AbstractIdentifiable;
import org.opensails.sails.util.Quick;

public class Model extends AbstractIdentifiable {
	protected String hiddenProperty = "hiddenValue";
	protected String passwordProperty = "passwordValue";
	protected String textProperty = "textValue";
	protected String textareaProperty = "textareaValue";
	protected boolean checkboxProperty = false;
	protected Collection<String> checkboxListProperty = Quick.list("two");
	protected String radioProperty = "one";
	protected String selectProperty = "selectValue";

	protected SubModel subModel = new SubModel();

	public static class SubModel {
		protected String textProperty = "textValue";
	}
}