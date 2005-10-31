package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

public class Checkbox extends CheckedElement<Checkbox> {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']checkbox[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Checkbox(String containerSource, String name) {
		super(containerSource, name);
	}

	public Checkbox value(String expected) {
		return this;
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
