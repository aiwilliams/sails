package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

public class TesterHidden extends TesterNamedElement {

	public TesterHidden(Element container, String name) {
		super(container, new XPathString("//input[@type='hidden'][@name='%s']", name), name);
	}

}
