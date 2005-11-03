/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import java.io.IOException;

import org.opensails.sails.html.AbstractHtmlElement;
import org.opensails.sails.html.HtmlGenerator;

/**
 * The super class of many of the form elements in this package.
 */
public abstract class FormElement<T extends FormElement> extends AbstractHtmlElement<T> implements IFormElement<T> {
	protected String name;

	protected FormElement(String elementName, String name) {
		super(elementName);
		this.name = name;
	}

	protected FormElement(String elementName, String name, String id) {
		super(elementName, id);
		this.name = name;
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
