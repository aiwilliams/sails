/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import java.io.IOException;
import java.io.Writer;

import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;

/**
 * An HTML INPUT of type CHECKBOX.
 * 
 * Checkboxes can be rendered in groups of one or more by name. If there is more
 * than one with the same name, the are submitted as a String[]. If they are by
 * themselves, they likely represent a boolean state, though they may not. One
 * of the challenges in making the display of a domain model work is knowing
 * whether, in the case of the boolean checkbox, the absence of a value on the
 * server side means that the box was displayed and then unchecked - the model
 * should have it's property set to false - or was never exposed at all. Of
 * course, if you are exposing every property for an object, then there is no
 * problem to solve.
 * 
 * Anywho, the {@link #getBoolean()} call will cause this to render a hidden
 * 'meta' field that the {@link org.opensails.sails.form.HtmlForm} class uses
 * when binding an HTTP form post to a model. If the checkbox is bound to a
 * boolean property, unchecking will remove that field from the posted form, but
 * the hidden will come back, indicating the the checkbox was rendered, but
 * unchecked.
 */
public class Checkbox extends LabelableInputElement<Checkbox> {
	public static final String CHECKBOX = "checkbox";
	public static final String CHECKED = "checked";
	public static final String FORM_META = HtmlForm.META_PREFIX + "cb.";

	protected boolean checked;
	protected Hidden hiddenForBoolean;

	/**
	 * @param name
	 */
	public Checkbox(String name) {
		super(RENDER_LABEL_AFTER, CHECKBOX, name);
		value = Boolean.TRUE.toString();
	}

	/**
	 * @param name
	 */
	public Checkbox(String name, String value) {
		super(RENDER_LABEL_AFTER, CHECKBOX, name);
		this.value = value;
	}

	public Checkbox checked() {
		return checked(true);
	}

	public Checkbox checked(boolean b) {
		checked = b;
		return this;
	}

	/**
	 * If called, the Checkbox is rendered with a Hidden. This technology allows
	 * for non-JavaScript maintenance of the checked state of a boolean property
	 * on an object. It is named with 'get' because boolean is a keyword. Has no
	 * effect if called multiple times.
	 */
	public Checkbox getBoolean() {
		if (hiddenForBoolean == null) {
			hiddenForBoolean = new Hidden(FORM_META + name);
			hiddenForBoolean.value(HtmlConstants.FALSE);
		}
		return this;
	}

	/**
	 * @param writer
	 * @throws IOException
	 */
	@Override
	public void toString(Writer writer) throws IOException {
		super.toString(writer);
		if (hiddenForBoolean != null) hiddenForBoolean.toString(writer);
	}

	@Override
	public Checkbox value(String value) {
		return super.value(value);
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		super.writeAttributes(generator);
		if (checked) generator.attribute(CHECKED, CHECKED);
	}
}
