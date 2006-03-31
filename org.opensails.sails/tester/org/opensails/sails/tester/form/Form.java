package org.opensails.sails.tester.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.AssertionFailedError;

import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.tester.html.FieldSet;

public class Form {
	public static final Pattern PATTERN = Pattern.compile("<form.*?>.*?</form>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	protected final HtmlForm form;
	protected final String source;

	public Form(String pageContent, HtmlForm form) {
		this.form = form;
		Matcher matcher = Form.PATTERN.matcher(pageContent);
		if (matcher.find()) this.source = matcher.group();
		else this.source = null;
	}

	public Checkbox checkbox(String named) {
		return new Checkbox(getSource(), named);
	}

	public Checkbox checkbox(String named, String value) {
		return new Checkbox(getSource(), named, value);
	}

	/**
	 * @param id
	 * @return a FieldSet limited to the scope of this form
	 */
	public FieldSet fieldSet(String id) {
		return new FieldSet(getSource(), id);
	}

	public String getSource() {
		return source;
	}

	public Hidden hidden(String named) {
		return new Hidden(getSource(), named);
	}

	public Password password(String named) {
		return new Password(getSource(), named);
	}

	public Radio radio(String named, String value) {
		return new Radio(getSource(), named, value);
	}

	public Select select(String named) {
		return new Select(getSource(), named);
	}

	public Submit submit(String labeled) {
		return new Submit(getSource(), labeled);
	}

	public Text text(String named) {
		return new Text(getSource(), named);
	}

	public Textarea textarea(String named) {
		return new Textarea(getSource(), named);
	}

	@Override
	public String toString() {
		return source;
	}

	public Form validated() {
		if (!form.isValid()) throw new AssertionFailedError(form.getErrorMessages());
		return this;
	}
}
