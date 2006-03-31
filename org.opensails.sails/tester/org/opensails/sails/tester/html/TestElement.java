package org.opensails.sails.tester.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TestElement<T extends TestElement> {
	protected String containerSource;
	protected String elementSource;
	protected String id;

	/**
	 * @param containerSource
	 * @param matchCriteria used to select the correct source for this element
	 *        when the pattern from {@link #getPattern()} matches multiple
	 *        times. This will be subclass specific.
	 */
	public TestElement(String containerSource, String matchCriteria) {
		initialize(containerSource, matchCriteria);
		this.id = HtmlPattern.readId(elementSource);
	}

	/**
	 * @param containerSource
	 * @param matchCriteria used to select the correct source for this element
	 *        when the pattern from {@link #getPattern()} matches multiple
	 *        times. This will be subclass specific.
	 * @param id expected id
	 */
	public TestElement(String containerSource, String matchCriteria, String id) {
		initialize(containerSource, matchCriteria);
		this.id = id;
	}

	/**
	 * For subclasses that have unique ways of finding themselves.
	 */
	protected TestElement() {}

	/**
	 * Finds the element source in the container source where the name attribute
	 * contains name.
	 * 
	 * @param name
	 * @return
	 */
	protected String elementSource(String matchCriteria) {
		Matcher matcher = getPattern().matcher(containerSource);
		while (matcher.find() && elementSource == null) {
			String element = matcher.group();
			if (elementSourceMatch(element, matchCriteria)) { return element; }
		}
		throw new NoSuchElementError(this.getClass(), containerSource, matchCriteria);
	}

	/**
	 * Subclasses may override to alter the way that the element source is
	 * matched. For instance, if you have an input type=text, then the default
	 * of matching the name works fine. For a submit, the matchCriteria is the
	 * label on the button, which is found in the input value attribute.
	 * 
	 * @param elementSource
	 * @param matchCriteria
	 * @return true if the element source matches the given matchCriteria
	 */
	protected boolean elementSourceMatch(String elementSource, String matchCriteria) {
		return new NameAttributeMatcher(elementSource).matches(matchCriteria);
	}

	/**
	 * @return the Pattern that describes this element in it's container
	 */
	protected abstract Pattern getPattern();

	protected void initialize(String containerSource, String matchCriteria) {
		this.containerSource = containerSource;
		this.elementSource = elementSource(matchCriteria);
	}
}
