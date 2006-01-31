package org.opensails.functional.components;

import org.opensails.sails.component.oem.*;

public class BasicComponent extends BaseComponent {
	public void initialize() {
		expose("exposedInBoth", "both from component");
		expose("exposedInComponent", "hello from component");
	}

	public void someCallback() {
		expose("callbackMade", "yes, callbackMade");
	}
}
