package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

import org.opensails.sails.tester.html.HtmlPattern;
import org.opensails.sails.tester.html.TestElementError;

public class Text extends TestFormElement<Text> {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']text[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Text(String formSource, String named) {
		super(formSource, named);
	}

	public Text assertValue(String expected) {
		if (!HtmlPattern.matchesValue(elementSource, expected)) throw new TestElementError(Text.class, elementSource, String.format("Value of text [%s] did not match [%s]", getName(), expected));
		return this;
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
