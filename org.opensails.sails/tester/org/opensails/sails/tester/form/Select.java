package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

public class Select extends TestFormElement<Select> {
	public static final Pattern PATTERN = Pattern.compile("<select.*?((/>)|(>.*?</select>))", Pattern.CASE_INSENSITIVE);
	protected OptionCollection options;

	public Select(String containerSource, String name) {
		super(containerSource, name);
	}

	public Select assertLabelsSelected(String... expected) {
		options().assertLabelsSelected(expected);
		return this;
	}

	public OptionCollection options() {
		if (options == null) options = new OptionCollection(elementSource);
		return options;
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
