package org.opensails.sails.form;

public class UnderscoreIdGenerator implements IFormElementIdGenerator {
	public String idForLabel(String label) {
		return label.replace(' ', '_');
	}

	public String idForName(String name) {
		return name.replace('.', '_');
	}
}
