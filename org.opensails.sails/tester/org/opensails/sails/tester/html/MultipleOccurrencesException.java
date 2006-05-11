package org.opensails.sails.tester.html;

import org.dom4j.Element;
import org.opensails.sails.tester.form.TesterElementError;

public class MultipleOccurrencesException extends TesterElementError {
	private static final long serialVersionUID = 1L;

	public MultipleOccurrencesException(XPathString xpath, Element containerElement) {
		super(xpath.toString(), containerElement);
	}
}
