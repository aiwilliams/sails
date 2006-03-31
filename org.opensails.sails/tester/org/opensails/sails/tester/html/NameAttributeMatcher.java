package org.opensails.sails.tester.html;

public class NameAttributeMatcher {
	protected String elementSource;

	public NameAttributeMatcher(String elementSource) {
		this.elementSource = elementSource;
	}

	public boolean matches(String matchCriteria) {
		return HtmlPattern.matchesName(elementSource, matchCriteria);
	}
}
