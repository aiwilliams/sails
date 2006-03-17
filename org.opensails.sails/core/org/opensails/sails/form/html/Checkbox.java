package org.opensails.sails.form.html;

import static org.opensails.sails.form.FormMeta.CHECKBOX_PREFIX;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;
import org.opensails.sails.util.Quick;

/**
 * An HTML INPUT of type CHECKBOX.
 * <p>
 * Checkboxes can be rendered in groups of one or more by name. If there is more
 * than one with the same name, the are submitted as a String[]. If they are by
 * themselves, they likely represent a boolean state, though they may not. One
 * of the challenges in making the display of a domain model work is knowing
 * whether, in the case of the boolean checkbox, the absence of a value on the
 * server side means that the box was displayed and then unchecked - the model
 * should have it's property set to false - or was never exposed at all. Of
 * course, if you are exposing every property for an object, then there is no
 * problem to solve.
 * <p>
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

	protected boolean checked;
	protected List<String> checkedValues;
	protected Hidden hiddenForBoolean;

	public Checkbox(String name) {
		this(name, null);
	}

	/**
	 * @param name
	 * @param checkedValues final output of checked attribute is determined by
	 *        whether the value of this checkbox is in checkedValues.
	 *        checkedValues can be null.
	 */
	public Checkbox(String name, String[] checkedValues) {
		super(RENDER_LABEL_AFTER, CHECKBOX, name);
		if (checkedValues != null) this.checkedValues = Quick.list(checkedValues);
		value = Boolean.TRUE.toString();
	}

	/**
	 * @see #checked(boolean)
	 */
	public Checkbox checked() {
		return checked(true);
	}

	/**
	 * Sets the checked attribute
	 * <p>
	 * This is ignored if the checkedValues is not null and has the value of
	 * this checkbox in it
	 * 
	 * @param b
	 * @return
	 */
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
			hiddenForBoolean = new Hidden(CHECKBOX_PREFIX + name);
			hiddenForBoolean.value(HtmlConstants.FALSE);
		}
		return this;
	}

	/**
	 * @param writer
	 * @throws IOException
	 */
	@Override
	public void renderThyself(Writer writer) throws IOException {
		super.renderThyself(writer);
		if (hiddenForBoolean != null) hiddenForBoolean.renderThyself(writer);
	}

	@Override
	public Checkbox value(Object value) {
		return super.value(value);
	}

	/**
	 * Generates the id using the name and value, as there may be multiple
	 * checkboxes on the page with the same name. This gives them a unique
	 * identifier within the document.
	 */
	@Override
	protected String guessId() {
		return FormElement.idForNameAndValue(getName(), getValue());
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		super.writeAttributes(generator);
		if (isActuallyChecked()) generator.attribute(CHECKED, CHECKED);
	}

	private boolean isActuallyChecked() {
		return checkedValues == null ? checked : checkedValues.contains(value);
	}
}
