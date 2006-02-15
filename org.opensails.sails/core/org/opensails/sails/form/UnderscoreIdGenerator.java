package org.opensails.sails.form;

import org.apache.commons.lang.StringUtils;

public class UnderscoreIdGenerator implements IFormElementIdGenerator {
	public String idForLabel(String label) {
		return label.replace(' ', '_');
	}

	public String idForName(String name) {
		return name.replace('.', '_');
	}

	public String idForStrings(String... strings) {
		return StringUtils.join(strings, '_');
	}
}
