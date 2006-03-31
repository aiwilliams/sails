package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

/**
 * Represents an HTML radio input.
 * <p>
 * Radio buttons should not exist in isolation. Therefore, you may only find
 * them with their name and value, as there will be 2 or more with the same
 * name.
 * 
 * @author aiwilliams
 */
public class Radio extends CheckedElement<Radio> {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']radio[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	public Radio(String containerSource, String name, String value) {
		super(containerSource, name, value);
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
