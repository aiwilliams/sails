package org.opensails.functional.components;

import org.opensails.sails.component.oem.BaseComponent;

public class BasicComponent extends BaseComponent {
	public void initialize(String argumentOne) {
		expose("exposedInBoth", "both from component");
		expose("exposedInComponent", "hello from component");
	}

	public void someCallback() {
		expose("callbackMade", "yes, callbackMade");
	}

	public void paramCallback(String one) {
		expose("callbackMade", one + " callbackMade");
	}
}
