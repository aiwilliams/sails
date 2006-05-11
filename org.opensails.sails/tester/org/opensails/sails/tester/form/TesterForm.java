package org.opensails.sails.tester.form;

import org.dom4j.Element;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.tester.html.XPathString;

public class TesterForm extends TesterNamedElement<TesterForm> {
	private HtmlForm htmlForm;

	public TesterForm(Element container, HtmlForm htmlForm) {
		super(container, "");
		this.htmlForm = htmlForm;
		if (container != null) element = loadElement(new XPathString("//form"));
	}

	public TesterForm(Element container, String name) {
		super(container, name);
		if (container != null) this.element = loadElement(new XPathString("//form[@name='%s']", name));
	}

	public TesterForm(Element container, String name, HtmlForm htmlForm) {
		this(container, name);
		this.htmlForm = htmlForm;
	}

	public void assertValid() {
		if (htmlForm != null && !htmlForm.isValid()) throw new TesterElementError(String.format("Form %s expected to be valid but is not", name), containerElement);
	}

	public TesterCheckbox checkbox(String named) {
		return new TesterCheckbox(element, named);
	}

	public TesterCheckbox checkbox(String named, String value) {
		return new TesterCheckbox(element, named, value);
	}

	public TesterFieldset fieldset(String id) {
		return new TesterFieldset(element, id);
	}

	public TesterHidden hidden(String named) {
		return new TesterHidden(element, named);
	}

	public TesterPassword password(String named) {
		return new TesterPassword(element, named);
	}

	public TesterRadio radio(String named, String value) {
		return new TesterRadio(element, named, value);
	}

	public TesterSelect select(String named) {
		return new TesterSelect(element, named);
	}

	public TesterSubmit submit(String name) {
		return new TesterSubmit(element, name);
	}

	public TesterText text(String name) {
		return new TesterText(element, name);
	}

	public TesterTextarea textarea(String named) {
		return new TesterTextarea(element, named);
	}
}
