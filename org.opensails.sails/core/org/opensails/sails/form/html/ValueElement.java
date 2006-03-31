package org.opensails.sails.form.html;

/**
 * FormElements that have a value attribute or whose content is it's value.
 * 
 * @author aiwilliams
 */
public abstract class ValueElement<T extends ValueElement> extends FormElement<T> {
	protected String value;

	/**
	 * @param elementName
	 * @param name
	 */
	public ValueElement(String elementName, String name) {
		super(elementName, name);
	}

	public String getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	public T value(Object value) {
		this.value = value == null ? "" : String.valueOf(value);
		return (T) this;
	}
}
