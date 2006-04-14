package org.opensails.sails.form.html;

import java.io.IOException;

import org.opensails.sails.html.AbstractHtmlElement;
import org.opensails.sails.html.HtmlGenerator;

/**
 * The super class of many of the form elements in this package.
 * 
 * @author aiwilliams
 */
public abstract class FormElement<T extends FormElement> extends AbstractHtmlElement<T> implements IFormElement<T> {
	protected String name;

	protected FormElement(String elementName, String name) {
		super(elementName);
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		generator.nameAttribute(getName());
		super.writeAttributes(generator);
	}
}
