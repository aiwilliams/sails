package org.opensails.sails.form;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.html.AbstractHtmlElement;

public class UnderscoreIdGenerator implements IElementIdGenerator {
	public String idForLabel(String label) {
		return label.replace(' ', '_');
	}

	public String idForName(String name) {
		return name.replace('.', '_');
	}

	// TODO Make this the only place this happens
	public String idForNameValue(String name, String value) {
		return AbstractHtmlElement.idForNameAndValue(name, value);
	}

	public String idForStrings(String... strings) {
		return StringUtils.join(strings, '_');
	}
}
