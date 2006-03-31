package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

import org.opensails.sails.tester.html.HtmlPattern;
import org.opensails.sails.tester.html.TestElementError;

public class Hidden extends TestFormElement<Hidden> {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']hidden[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Hidden(String formSource, String named) {
		super(formSource, named);
	}

	public Hidden assertValue(String expected) {
		if (!HtmlPattern.matchesValue(elementSource, expected)) throw new TestElementError(Text.class, elementSource, String.format("Value of text [%s] did not match [%s]", getName(), expected));
		return this;
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
