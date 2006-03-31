package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

import org.opensails.sails.tester.html.HtmlPattern;
import org.opensails.sails.tester.html.TestElementError;

public class Textarea extends TestFormElement<Textarea> {
	public static final Pattern PATTERN = Pattern.compile("<textarea.*?>(.*?)</textarea>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public Textarea(String containerSource, String name) {
		super(containerSource, name);
	}

	public Textarea assertValue(String expected) {
		if (!HtmlPattern.matchesBody(elementSource, expected)) throw new TestElementError(Textarea.class, elementSource, String.format("Value of textarea [%s] did not match [%s]", getName(), expected));
		return this;
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
