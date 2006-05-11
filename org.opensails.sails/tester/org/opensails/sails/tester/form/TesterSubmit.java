package org.opensails.sails.tester.form;

import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.opensails.sails.tester.html.MultipleOccurrencesException;
import org.opensails.sails.tester.html.NoSuchElementError;
import org.opensails.sails.tester.html.XPathString;

public class TesterSubmit extends TesterNamedElement {

	public TesterSubmit(Element container, String name) {
		super(container, name);

		xpath = new XPathString("//input[@type='submit'][@name='%s']", name);
		List<Node> elements = containerElement.selectNodes(xpath.toString());
		if (elements == null || elements.isEmpty()) {
			xpath = new XPathString("//input[@type='submit'][@value='%s']", name);
			elements = containerElement.selectNodes(xpath.toString());
			if (elements == null || elements.isEmpty()) throw new NoSuchElementError(xpath, containerElement);
		}
		if (elements.size() > 1) throw new MultipleOccurrencesException(xpath, containerElement);
		element = (Element) elements.get(0);
	}

}
