package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

public class TesterFieldset extends TesterElement {
	public TesterFieldset(Element container, String id) {
		super(container, new XPathString("//fieldset[@id='%s']", id));
	}
}
