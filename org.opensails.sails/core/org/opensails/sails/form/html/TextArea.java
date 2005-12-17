/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import java.io.IOException;
import java.io.Writer;

import org.opensails.sails.html.HtmlGenerator;

/**
 * An HTML TEXTAREA.
 */
public class TextArea extends ValueElement<TextArea> implements Labelable<TextArea> {
	public static final String TEXTAREA = "textarea";

	protected Label label;

	/**
	 * @param name
	 */
	public TextArea(String name) {
		super(TEXTAREA, name);
	}

	@Override
	public String getId() {
		return id;
	}

	public TextArea label(String text) {
		label = new Label(this).text(text);
		return this;
	}

	@Override
	public void toString(Writer writer) throws IOException {
		if (label != null) label.toString(writer);
		render(writer);
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		if (value != null) generator.write(value);
	}

	@Override
	protected boolean hasBody() {
		return true;
	}
}
