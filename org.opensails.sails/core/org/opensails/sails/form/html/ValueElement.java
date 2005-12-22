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

	public String getValue() {
		return value;
	}

	public T value(Object value) {
		this.value = value == null ? null : value.toString();
		return (T) this;
	}
}
