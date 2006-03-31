package org.opensails.sails.tester.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.AssertionFailedError;

import org.opensails.sails.tester.html.HtmlPattern;
import org.opensails.sails.tester.html.MultipleElementOccurrencesException;
import org.opensails.sails.tester.html.NoSuchElementError;
import org.opensails.sails.tester.html.TestElementError;

public abstract class CheckedElement<T extends CheckedElement> extends TestFormElement<T> {
	public static final Pattern CHECKED_PATTERN = Pattern.compile(" checked=[\"|'](.*?)[\"|']", Pattern.CASE_INSENSITIVE);

	public CheckedElement(String containerSource, String name) {
		super(name);
		this.containerSource = containerSource;

		Matcher matcher = getPattern().matcher(containerSource);
		while (matcher.find())
			if (HtmlPattern.matchesName(matcher.group(), name)) {
				if (elementSource != null) throw new MultipleElementOccurrencesException(getClass(), containerSource, name);
				elementSource = matcher.group();
			}
		if (elementSource == null) throw new NoSuchElementError(getClass(), containerSource, String.format("name=\"%s\"", name));
		this.id = HtmlPattern.readId(elementSource);
	}

	public CheckedElement(String containerSource, String name, String value) {
		super(name);
		this.containerSource = containerSource;

		Matcher matcher = getPattern().matcher(containerSource);
		while (matcher.find())
			if (HtmlPattern.matchesName(matcher.group(), name) && HtmlPattern.matchesValue(matcher.group(), value)) {
				elementSource = matcher.group();
				break;
			}
		if (elementSource == null) throw new NoSuchElementError(getClass(), containerSource, String.format("name=\"%s\" and value=\"%s\"", name, value));
		this.id = HtmlPattern.readId(elementSource);
	}

	public T assertChecked() {
		return assertChecked(true);
	}

	/**
	 * @param expected checked state
	 * @return this for more asserting
	 * @see #assertChecked()
	 * @see #assertUnchecked()
	 */
	@SuppressWarnings("unchecked")
	public T assertChecked(boolean expected) {
		assertChecked(expected, CHECKED_PATTERN.matcher(elementSource).find());
		return (T) this;
	}

	public T assertUnchecked() {
		return assertChecked(false);
	}

	@SuppressWarnings("unchecked")
	public T assertValue(String expected) {
		if (!HtmlPattern.matchesValue(elementSource, expected)) throw new TestElementError(getClass(), elementSource, String.format("Value of [%s] did not match [%s]", getName(), expected));
		return (T) this;
	}

	protected void assertChecked(boolean expected, boolean isChecked) throws AssertionFailedError {
		if (expected && !isChecked) throw new AssertionFailedError("Expected " + getName() + " to be checked but was not. Source is:\n" + elementSource);
		else if (!expected && isChecked) throw new AssertionFailedError("Expected " + getName() + " not to be checked but was. Source is:\n" + elementSource);
	}
}
