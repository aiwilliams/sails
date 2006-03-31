package org.opensails.sails.tester.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opensails.sails.tester.html.HtmlPattern;

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

	public String getValue() {
		Matcher matcher = HtmlPattern.ATTRIBUTE_VALUE.matcher(optionSource);
		if (matcher.find()) return matcher.group(1);
		return null;
	}

	public boolean isSelected() {
		return optionSource.contains("selected");
	}

	@Override
	public String toString() {
		return optionSource;
	}
}
