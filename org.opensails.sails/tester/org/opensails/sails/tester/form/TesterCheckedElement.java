package org.opensails.sails.tester.form;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

@SuppressWarnings("unchecked")
public class TesterCheckedElement<E extends TesterCheckedElement> extends TesterNamedElement {
	protected final String value;
	protected final String type;

	public TesterCheckedElement(String type, Element container, String name) {
		super(container, new XPathString("//input[@type='%s'][@name='%s']", type, name), name);
		this.type = type;
		this.value = "*";
	}

	public TesterCheckedElement(String type, Element container, String name, String value) {
		super(container, new XPathString("//input[@type='%s'][@name='%s'][@value='%s']", type, name, value), name);
		this.type = type;
		this.value = value;
	}

	public E assertChecked() {
		return assertChecked(true);
	}

	public E assertChecked(boolean expected) {
		Attribute attribute = element.attribute("checked");
		boolean isChecked = attribute != null;
		if (expected && !isChecked) throw new TesterElementError(String.format("Expected %s to be checked but was not.", name), element);
		else if (!expected && isChecked) throw new TesterElementError(String.format("Expected %s not to be checked but was.", name), element);
		return (E) this;
	}

	public E assertUnchecked() {
		return assertChecked(false);
	}

}
