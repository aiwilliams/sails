package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

public class TesterTextarea extends TesterNamedElement {

	public TesterTextarea(Element container, String name) {
		super(container, new XPathString("//textarea[@name='%s']", name), name);
	}

	public TesterTextarea assertValue(String expected) {
		String actual = element.getText();
		if (expected == null && actual == null) return this;
		if (expected != null && expected.equals(actual)) return this;
		throw new TesterElementError(String.format("Expected %s to have the value [%s] but was [%s]", expected, actual), element);
	}
}
