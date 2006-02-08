package org.opensails.component.tester;

import org.opensails.sails.component.oem.BaseComponent;

public class Component extends BaseComponent {
	public void initialize() {}

	@Override
	public String renderThyself() {
		return ""; // we don't want a view
	}
}
