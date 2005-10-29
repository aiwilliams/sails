package org.opensails.sails.tester.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlPattern {
	public static final Pattern ATTRIBUTE_ID = Pattern.compile("id=[\"|'](.*?)[\"|']", Pattern.CASE_INSENSITIVE);
	public static final Pattern ATTRIBUTE_NAME = Pattern.compile("name=[\"|'](.*?)[\"|']", Pattern.CASE_INSENSITIVE);
	public static final Pattern ATTRIBUTE_VALUE = Pattern.compile("value=[\"|'](.*?)[\"|']", Pattern.CASE_INSENSITIVE);
	public static final Pattern LABEL = Pattern.compile("<label for=\"(.*?)\">(.*?)</label>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static boolean matchesName(String source, String expected) {
		Matcher matcher = HtmlPattern.ATTRIBUTE_NAME.matcher(source);
		if (matcher.find()) return matcher.group(1).equals(expected);
		return false;
	}

	public static boolean matchesValue(String source, String expected) {
		Matcher matcher = HtmlPattern.ATTRIBUTE_VALUE.matcher(source);
		if (matcher.find()) return matcher.group(1).equals(expected);
		return false;
	}

	public static String readId(String source) {
		Matcher matcher = ATTRIBUTE_ID.matcher(source);
		return matcher.find() ? matcher.group(1) : null;
	}
}
