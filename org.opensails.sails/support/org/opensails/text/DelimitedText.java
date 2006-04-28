package org.opensails.text;

/**
 * A StringBuilder wrapper that places a delimiter between each append.
 * 
 * @author aiwilliams
 */
public class DelimitedText {
	protected final StringBuilder t;
	protected final String delimiter;

	public DelimitedText(String delimiter) {
		this.delimiter = delimiter;
		t = new StringBuilder();
	}

	public DelimitedText(String delimiter, int initialSize) {
		this.delimiter = delimiter;
		t = new StringBuilder(initialSize);
	}

	public void append(String string) {
		append(string, t.length() != 0);
	}

	public void append(String string, boolean delimit) {
		if (delimit) t.append(delimiter);
		t.append(string);
	}

	@Override
	public String toString() {
		return t.toString();
	}
}
