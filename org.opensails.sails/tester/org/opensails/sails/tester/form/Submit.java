package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

import org.opensails.sails.tester.html.HtmlPattern;

public class Submit extends TestFormElement {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']submit[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Submit(String formSource, String label) {
		super(formSource, label);
	}

	@Override
	protected boolean elementSourceMatch(String element, String matchCriteria) {
		return HtmlPattern.matchesValue(element, matchCriteria);
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
