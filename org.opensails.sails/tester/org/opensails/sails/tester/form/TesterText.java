package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

public class TesterText extends TesterNamedElement {

	public TesterText(Element container, String name) {
		super(container, new XPathString("//input[@type='text'][@name='%s']", name), name);
	}

}
