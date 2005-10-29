package org.opensails.sails.tester.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.AssertionFailedError;

import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.tester.html.SourceContentError;

public class Form {
	public static final Pattern PATTERN = Pattern.compile("<form.*?>.*?</form>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	protected final HtmlForm form;
	protected final String source;

	public Form(String pageContent) {
		Matcher matcher = Form.PATTERN.matcher(pageContent);
		if (!matcher.find()) throw new SourceContentError(pageContent, "There is not a form in the content");
		this.source = matcher.group();
		this.form = null;
	}

	public Form(String pageContent, HtmlForm form) {
		this.form = form;
		Matcher matcher = Form.PATTERN.matcher(pageContent);
		if (matcher.find()) this.source = matcher.group();
		else this.source = null;
	}

	public String getSource() {
		return source;
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

	public Form validated() {
		if (!form.isValid()) throw new AssertionFailedError(form.getMessage());
		return this;
	}
}
