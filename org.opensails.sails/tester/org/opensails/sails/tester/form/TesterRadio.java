package org.opensails.sails.tester.form;

import org.dom4j.Element;

public class TesterRadio extends TesterCheckedElement {
	public TesterRadio(Element container, String name, String value) {
		super("radio", container, name, value);
	}
}
