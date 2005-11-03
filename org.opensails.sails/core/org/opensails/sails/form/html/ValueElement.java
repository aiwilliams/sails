/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

/**
 * FormElements that have a value attribute or whose content is it's value.
 */
@SuppressWarnings("unchecked")
public abstract class ValueElement<T extends ValueElement> extends FormElement<T> {
	protected String value;

	/**
	 * @param elementName
	 * @param name
	 */
	public ValueElement(String elementName, String name) {
		super(elementName, name);
	}

	/**
	 * @param elementName
	 * @param name
	 * @param id
	 */
	public ValueElement(String elementName, String name, String id) {
		super(elementName, name, id);
	}

	public String getValue() {
		return value;
	}

	public T value(boolean value) {
		this.value = Boolean.toString(value);
		return (T) this;
	}

	public T value(String value) {
		this.value = value;
		return (T) this;
	}
}
