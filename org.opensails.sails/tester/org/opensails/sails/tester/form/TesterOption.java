package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

public class TesterOption extends TesterElement<TesterOption> {

	public TesterOption(Element container, Element element) {
		super(container);
		this.element = element;
		this.xpath = new XPathString("//option[@value='%s'", getValue());
	}

	public String getLabel() {
		return element.getText();
	}

	public boolean isSelected() {
		return element.attribute("selected") != null;
	}

}
