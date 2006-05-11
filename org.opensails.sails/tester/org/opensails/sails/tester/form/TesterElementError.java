package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.SailsException;
import org.opensails.sails.tester.html.XPathString;

public class TesterElementError extends SailsException {
	private static final long serialVersionUID = 1L;

	protected final Element containerElement;

	public TesterElementError(String message, Element containerElement) {
		super(message);
		this.containerElement = containerElement;
	}

	public TesterElementError(XPathString elementDescription, Element containerElement, String message) {
		this(String.format("%s with container content of\n%s", message, containerElement.asXML()), containerElement);
	}
}
