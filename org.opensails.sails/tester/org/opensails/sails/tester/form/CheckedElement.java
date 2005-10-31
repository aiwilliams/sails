package org.opensails.sails.tester.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.AssertionFailedError;

import org.opensails.sails.tester.html.HtmlPattern;
import org.opensails.sails.tester.html.NoSuchElementError;

public abstract class CheckedElement<T extends CheckedElement> extends TestFormElement<T> {
	public static final Pattern CHECKED_PATTERN = Pattern.compile(" checked=[\"|'](.*?)[\"|']", Pattern.CASE_INSENSITIVE);

	protected String activeElementSource;
	protected int elementCount;

	public CheckedElement(String containerSource, String name) {
		super(containerSource, name);
	}

	@SuppressWarnings("unchecked")
	public T checked(boolean expected) {
		if (activeElementSource == null) throw new NoSuchElementError(this.getClass(), containerSource, elementSource);
		assertChecked(expected, CHECKED_PATTERN.matcher(activeElementSource).find());
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T value(String expected) {
		if (elementCount > 0) {
			activeElementSource = null;
			Matcher matcher = getPattern().matcher(elementSource);
			while (matcher.find() && activeElementSource == null) {
				String element = matcher.group();
				if (HtmlPattern.matchesValue(element, expected)) {
					activeElementSource = element;
					return (T) this;
				}
			}
		}
		throw new NoSuchElementError(this.getClass(), containerSource, expected);
	}

	protected void assertChecked(boolean expected, boolean isChecked) throws AssertionFailedError {
		if (expected && !isChecked) throw new AssertionFailedError("Expected " + getName() + " to be checked but was not. Source is:\n" + elementSource);
		else if (!expected && isChecked) throw new AssertionFailedError("Expected " + getName() + " not to be checked but was. Source is:\n" + elementSource);
	}

	/**
	 * @inheritDoc
	 * 
	 * Checked elements may have more than one source element. These get
	 * combined to form the 'complete' element source.
	 */
	@Override
	protected String elementSource(String matchCriteria) {
		StringBuilder source = new StringBuilder();
		Matcher matcher = getPattern().matcher(containerSource);
		while (matcher.find() && elementSource == null) {
			String element = matcher.group();
			if (elementSourceMatch(element, matchCriteria)) {
				source.append(element);
				elementCount++;
			}
		}
		if (elementCount > 0) {
			if (elementCount == 1) activeElementSource = source.toString();
			return source.toString();
		} else throw new NoSuchElementError(this.getClass(), containerSource, matchCriteria);
	}
}
