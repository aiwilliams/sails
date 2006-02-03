package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

import org.opensails.sails.tester.html.HtmlPattern;
import org.opensails.sails.tester.html.TestElementError;

public class Password extends TestFormElement<Password> {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']password[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Password(String formSource, String named) {
		super(formSource, named);
	}

	public void value(String expected) {
		if (!HtmlPattern.matchesValue(elementSource, expected)) throw new TestElementError(Password.class, elementSource, String.format("Value of password [%s] did not match [%s]", getName(), expected));
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}

}

