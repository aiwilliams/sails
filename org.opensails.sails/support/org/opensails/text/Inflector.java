package org.opensails.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opensails.sails.SailsException;
import org.opensails.sails.util.BleedingEdgeException;

public class Inflector {
	protected static final List<Inflection> PLURALS = new ArrayList<Inflection>();

	static {
		plural("^(.*)$", "\\1s");
	}

	public static void plural(String regex, String replacement) {
		PLURALS.add(0, new Inflection(regex, replacement));
	}

	public static String pluralize(int count, String singular) {
		if (count == 1) return String.format("1 %s", count, singular);
		return String.format("%d %s", count, pluralize(singular));
	}

	public static String pluralize(int count, String singular, String plural) {
		throw new BleedingEdgeException("implement");
	}

	public static String pluralize(String singular) {
		for (Inflection inflection : PLURALS) {
			String plural = inflection.perform(singular);
			if (plural != null) return plural;
		}
		throw new SailsException("Huh? Should at least put s on end of word");
	}

	public static class Inflection {
		protected final Pattern pattern;
		protected final String replacement;

		public Inflection(String regex, String replacement) {
			this.replacement = replacement;
			pattern = Pattern.compile(regex);
		}

		/**
		 * @param word
		 * @return inflected word, or null if nothing done
		 */
		public String perform(String word) {
			Matcher matcher = pattern.matcher(word);
			if (!matcher.matches()) return null;
			return matcher.replaceFirst(replacement);
		}
	}

}
