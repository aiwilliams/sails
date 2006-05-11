package org.opensails.sails.tester.form;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.opensails.sails.tester.html.MultipleOccurrencesException;
import org.opensails.sails.tester.html.NoSuchElementError;
import org.opensails.sails.tester.html.XPathString;

@SuppressWarnings("unchecked")
public abstract class TesterElement<E extends TesterElement> {
	protected Element element;
	protected Element containerElement;
	protected XPathString xpath;

	/**
	 * @param container the Element that contains this element having name
	 * @param xpath TODO
	 * @param name the value of the name attribute of this element
	 */
	public TesterElement(Element container, XPathString xpath) {
		this.containerElement = container;
		this.xpath = xpath;
		this.element = loadElement(xpath);
	}

	/**
	 * Allows for subclass control of the xpath and element assignment
	 * 
	 * @param container
	 */
	protected TesterElement(Element container) {
		this.containerElement = container;
	}

	public E assertLabeled(String expected) {
		String id = getIdOrFail("Cannot find label for form element without id");
		Element node = (Element) containerElement.selectSingleNode(String.format("//label[@for='%s']", id));
		if (node == null) throw new TesterElementError(xpath, containerElement, String.format("No label found for element having id '%s'", id));

		String actual = node.node(0).getText();
		if (!actual.equals(expected)) throw new TesterElementError(xpath, containerElement, String.format("Found a label for %s, but value did not match. Expected [%s] but was [%s]", id, expected, actual));
		return (E) this;
	}

	public E assertValue(String expected) {
		String value = getValue();
		if (!expected.equals(value)) throw new TesterElementError(xpath, containerElement, String.format("Expected value to equal [%s] but was [%s]", expected, value));
		return (E) this;
	}

	public String getValue() {
		return element.attribute("value").getValue();
	}

	public String source() {
		return element.asXML();
	}

	protected String getId() {
		Attribute id = element.attribute("id");
		return id == null ? null : id.getValue();
	}

	protected String getIdOrFail(String failureMessage) {
		String id = getId();
		if (id == null) throw new TesterElementError(xpath, containerElement, failureMessage);
		return id;
	}

	protected Element loadElement(XPathString xpath) {
		List<Node> elements = containerElement.selectNodes(xpath.toString());
		if (elements == null || elements.isEmpty()) throw new NoSuchElementError(xpath, containerElement);
		if (elements.size() > 1) throw new MultipleOccurrencesException(xpath, containerElement);
		return (Element) elements.get(0);
	}

}
