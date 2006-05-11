package org.opensails.sails.tester.form;

import org.dom4j.Element;

public class TesterCheckbox extends TesterCheckedElement {
	public TesterCheckbox(Element container, String name) {
		super("checkbox", container, name);
	}

	public TesterCheckbox(Element container, String name, String value) {
		super("checkbox", container, name, value);
	}
}
