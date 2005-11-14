package org.opensails.sails.tester.form;

import java.util.regex.Matcher;

import org.opensails.sails.tester.html.HtmlPattern;
import org.opensails.sails.tester.html.TestElement;
import org.opensails.sails.tester.html.TestElementError;

/**
 * The superclass of TestElements that are direct descendants of html form.
 * 
 * @param <T>
 */
public abstract class TestFormElement<T extends TestFormElement> extends TestElement<T> {
	protected final String name;

	/**
	 * @param containerSource the source of the html form this form element
	 *        belongs to
	 * @param name
	 */
	public TestFormElement(String containerSource, String name) {
		super(containerSource, name);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Asserts that this element has a label with expected value. This is done
	 * differently by elements.
	 * 
	 * @param expected
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T labeled(String expected) {
		if (id == null) throw new TestElementError(getClass(), containerSource, "Cannot find label for form element without id");
		Matcher matcher = HtmlPattern.LABEL.matcher(containerSource);
		while (matcher.find()) {
			if (matcher.group(1).equals(id)) {
				if (matcher.group(2).equals(expected)) return (T) this;
				else throw new TestElementError(getClass(), containerSource, String.format("Found a label for %s, but value did not match. Expected [%s] but was [%s]", id, expected, matcher.group(2)));
			}
		}
		throw new TestElementError(getClass(), containerSource, "Label for " + id + " does not exist");
	}
}
