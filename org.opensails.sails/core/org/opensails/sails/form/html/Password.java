package org.opensails.sails.form.html;

/**
 * An HTML INPUT of type PASSWORD.
 */
public class Password extends LabelableInputElement<Password> {
	public static final String PASSWORD = "password";

	public Password(String name) {
		super(RENDER_LABEL_BEFORE, PASSWORD, name);
	}

	public Password(String name, String id) {
		super(RENDER_LABEL_BEFORE, PASSWORD, name, id);
	}
}

