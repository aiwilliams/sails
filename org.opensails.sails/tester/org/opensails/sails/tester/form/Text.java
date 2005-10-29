package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

public class Text extends TestFormElement {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']text[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Text(String formSource, String named) {
		super(formSource, named);
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
