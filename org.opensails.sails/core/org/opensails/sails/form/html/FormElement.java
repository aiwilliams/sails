/*
 * Created on May 15, 2005
 *
 * (c) 2005 opensails.org
 */
package org.opensails.sails.form.html;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
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
		if (StringUtils.isBlank(id)) {
			String temp = guessId();
			if (!StringUtils.isBlank(temp)) id = temp;
		}
		return id;
	}

	public String getName() {
		return name;
	}

	/**
	 * Guesses the value of the id to be the name.
	 * <p>
	 * Subclasses may override if they would like to guess the id differently.
	 * 
	 * @return a best-guess value for id if it is null when getId() is called,
	 *         null if no guess
	 * @see AbstractHtmlElement#idForName(String)
	 */
	protected String guessId() {
		return AbstractHtmlElement.idForName(getName());
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		generator.nameAttribute(getName());
		super.writeAttributes(generator);
	}
}
