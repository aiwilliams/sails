package org.opensails.sails.tester.html;

import java.util.regex.Pattern;

public class FieldSet extends TestElement<FieldSet> {
	public static final Pattern PATTERN = Pattern.compile("<fieldset.*?>(.*?)</fieldset>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public FieldSet(String containerSource, String id) {
		super(containerSource, id, id);
	}

	@Override
	protected boolean elementSourceMatch(String element, String matchCriteria) {
		return HtmlPattern.matchesId(element, matchCriteria);
	}
	
	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
