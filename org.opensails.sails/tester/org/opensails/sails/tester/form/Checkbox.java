package org.opensails.sails.tester.form;

import java.util.regex.Pattern;

public class Checkbox extends CheckedElement<Checkbox> {
	public static final Pattern PATTERN = Pattern.compile("<input.*?type=[\"|']checkbox[\"|'].*?((/>)|(>.*?</input>))", Pattern.CASE_INSENSITIVE);

	/**
	 * A checkbox in containerSource having attribute name=name.
	 * <p>
	 * This constructor is useful in cases where there is only one checkbox have
	 * name=name. If there are more than one with the same name, you must
	 * specify the value as well.
	 * 
	 * @param containerSource
	 * @param name
	 */
	public Checkbox(String containerSource, String name) {
		super(containerSource, name);
	}

	/**
	 * A checkbox in containerSource having attribute name=name and value=value.
	 * <p>
	 * This constructor is useful in cases where there are multiple checkboxes
	 * having name=name. Of course, it will also work when there is only one.
	 * 
	 * @param containerSource
	 * @param name
	 * @param value
	 */
	public Checkbox(String containerSource, String name, String value) {
		super(containerSource, name, value);
	}

	@Override
	protected Pattern getPattern() {
		return PATTERN;
	}
}
