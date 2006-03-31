package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

import org.opensails.sails.tester.html.HtmlPattern;
import org.opensails.sails.tester.html.TestElementError;

public class Password extends TestFormElement<Password> {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']password[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Password(String formSource, String named) {
		super(formSource, named);
	}

	public Password assertValue(String expected) {
		if (!HtmlPattern.matchesValue(elementSource, expected)) throw new TestElementError(Password.class, elementSource, String.format("Value of password [%s] did not match [%s]", getName(), expected));
		return this;
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}

}
