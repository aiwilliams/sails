package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

public class Textarea extends TestFormElement {
	public static final Pattern PATTERN = Pattern.compile("<textarea.*?>(.*?)</textarea>", Pattern.CASE_INSENSITIVE);

	public Textarea(String containerSource, String name) {
		super(containerSource, name);
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
