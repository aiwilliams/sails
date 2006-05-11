package org.opensails.sails.tester.html;

import org.dom4j.Element;
import org.opensails.sails.tester.form.TesterElementError;

public class NoSuchElementError extends TesterElementError {
	private static final long serialVersionUID = 1L;

	public NoSuchElementError(XPathString elementDescription, Element containerElement) {
		super(String.format("No element '%s' found in\n%s", elementDescription, containerElement.asXML()), containerElement);
	}

}
