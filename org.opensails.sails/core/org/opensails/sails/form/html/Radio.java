package org.opensails.sails.form.html;

import java.io.IOException;

import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;

/**
 * An HTML INPUT of type RADIO.
 */
public class Radio extends LabelableInputElement<Radio> {
	public static final String RADIO = "radio";
	protected boolean checked;

	/**
	 * @param name
	 * @param value required for a radio
	 */
	public Radio(String name, String value) {
		super(RENDER_LABEL_AFTER, RADIO, name);
		value(value);
	}

	/**
	 * @param name
	 * @param value required for a radio
	 * @param id
	 */
	public Radio(String name, String value, String id) {
		super(RENDER_LABEL_AFTER, RADIO, name, id);
		value(value);
	}

	/**
	 * @see #checked(boolean)
	 */
	public Radio checked() {
		return checked(true);
	}

	/**
	 * Sets the checked attribute
	 * 
	 * @param b
	 * @return
	 */
	public Radio checked(boolean b) {
		checked = b;
		return this;
	}

	/**
	 * Generates the id using the name and value, as there may be multiple
	 * radios on the page with the same name. This gives them a unique
	 * identifier within the document.
	 */
	@Override
	protected String guessId() {
		return FormElement.idForNameAndValue(getName(), getValue());
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		super.writeAttributes(generator);
		if (checked) generator.attribute(HtmlConstants.CHECKED, HtmlConstants.CHECKED);
	}
}
