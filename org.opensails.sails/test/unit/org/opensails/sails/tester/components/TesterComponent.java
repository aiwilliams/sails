package org.opensails.sails.tester.components;

import org.opensails.sails.component.oem.*;

public class TesterComponent extends BaseComponent {
	public void exception() {
		throw new RuntimeException("Simulated runtime exception");
	}
}
