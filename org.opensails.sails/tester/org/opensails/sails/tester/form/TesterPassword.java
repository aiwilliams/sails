package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

public class TesterPassword extends TesterNamedElement {

	public TesterPassword(Element container, String name) {
		super(container, new XPathString("//input[@type='password'][@name='%s']", name), name);
	}

}
