package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

public class Select extends TestFormElement<Select> {
	public static final Pattern PATTERN = Pattern.compile("<select.*?((/>)|(>.*?</select>))", Pattern.CASE_INSENSITIVE);

	public Select(String containerSource, String name) {
		super(containerSource, name);
	}

	public OptionCollection options() {
		return new OptionCollection(elementSource);
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
