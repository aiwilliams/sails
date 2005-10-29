package org.opensails.sails.tester.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Option {
	public static final Pattern LABEL_PATTERN = Pattern.compile(">(.*?)<");

	protected String optionSource;

	public Option(String optionSource) {
		this.optionSource = optionSource;
	}

	public String getLabel() {
		Matcher matcher = LABEL_PATTERN.matcher(optionSource);
		if (matcher.find()) return matcher.group(1);
		return null;
	}

	@Override
	public String toString() {
		return optionSource;
	}
}
