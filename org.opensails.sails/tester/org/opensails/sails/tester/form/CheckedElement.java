package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

import junit.framework.AssertionFailedError;

public abstract class CheckedElement<T extends CheckedElement> extends TestFormElement<T> {
	public static final Pattern CHECKED_PATTERN = Pattern.compile(" checked=[\"|'](.*?)[\"|']", Pattern.CASE_INSENSITIVE);

	public CheckedElement(String containerSource, String name) {
		super(containerSource, name);
	}

	@SuppressWarnings("unchecked")
	public T checked(boolean expected) {
		boolean isChecked = CHECKED_PATTERN.matcher(elementSource).find();
		if (expected && !isChecked) throw new AssertionFailedError("Expected " + getName() + " to be checked but was not");
		else if (!expected && isChecked) throw new AssertionFailedError("Expected " + getName() + " not to be checked but was");
		return (T) this;
	}
}
