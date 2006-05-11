package org.opensails.sails.tester;

import org.dom4j.Element;
import org.opensails.sails.tester.form.TesterElement;

public class TesterScript extends TesterElement<TesterScript> {
	public TesterScript(Element container, Element element) {
		super(container);
		this.element = element;
	}

	public String getSrc() {
		return this.element.attributeValue("src");
	}
}
