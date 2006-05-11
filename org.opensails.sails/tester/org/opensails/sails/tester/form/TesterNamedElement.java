package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

public abstract class TesterNamedElement<E extends TesterNamedElement> extends TesterElement<E> {
	protected final String name;

	/**
	 * @param container the Element that contains this element having name
	 * @param xpath the xpath to the element
	 * @param name the value of the name attribute of this element
	 */
	public TesterNamedElement(Element container, XPathString xpath, String name) {
		super(container, xpath);
		this.name = name;
	}

	/**
	 * Allows for subclass control of the xpath and element assignment
	 */
	protected TesterNamedElement(Element container, String name) {
		super(container);
		this.name = name;
	}
}
