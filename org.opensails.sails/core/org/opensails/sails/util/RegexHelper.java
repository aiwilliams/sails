package org.opensails.sails.util;

import java.util.regex.Pattern;

public class RegexHelper {
	/**
	 * Pretty much every match I want to do, with the tester at least, is a
	 * "contains" match. This may modify your regex. The plan is to make it
	 * semi-intelligent, like if your regex starts with ^, we won't muck with
	 * it.
	 */
	public static boolean containsMatch(String subject, String regex) {
		if (subject == null) return false;
		return containsPattern(regex).matcher(subject).find();
	}

	public static Pattern containsPattern(String regex) {
		if (regex.startsWith("(?") || regex.startsWith("^")) return Pattern.compile(regex);
		return Pattern.compile(regex, Pattern.DOTALL);
	}
}
