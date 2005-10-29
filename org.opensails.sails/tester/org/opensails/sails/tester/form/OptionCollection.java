package org.opensails.sails.tester.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opensails.sails.tester.html.SourceContentError;

public class OptionCollection {
	public static final Pattern PATTERN = Pattern.compile("<option.*?>.*?</option>", Pattern.CASE_INSENSITIVE);

	protected String containerSource;
	protected List<Option> options;

	/**
	 * @param containerSource the html source of the element containing the
	 *        options (i.e. select, radio)
	 */
	public OptionCollection(String containerSource) {
		this.containerSource = containerSource;
		options = new ArrayList<Option>();
		Matcher matcher = PATTERN.matcher(containerSource);
		while (matcher.find())
			options.add(new Option(matcher.group()));
	}

	public OptionCollection labels(String... expected) {
		List<String> labelsPresent = new ArrayList<String>();
		for (Option option : options)
			labelsPresent.add(option.getLabel());
		if (labelsPresent.size() != expected.length) throw new SourceContentError(containerSource, String.format("Expected %s labels but there were %s", expected.length, labelsPresent.size()));
		if (!Arrays.asList(expected).equals(labelsPresent)) throw new SourceContentError(containerSource, "Label count was same but not order or content");
		return this;
	}

	public int size() {
		return options.size();
	}

	public void size(int expected) {
		int actualSize = size();
		if (expected != actualSize) throw new SourceContentError(containerSource, String.format("Expected %s labels but there were %s", expected, actualSize));
	}
}