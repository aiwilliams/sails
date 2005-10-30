package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

public class Radio extends CheckedElement<Radio> {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']radio[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Radio(String containerSource, String name) {
		super(containerSource, name);
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
