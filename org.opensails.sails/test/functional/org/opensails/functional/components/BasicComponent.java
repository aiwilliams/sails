package org.opensails.functional.components;

import org.opensails.sails.component.oem.BaseComponent;

public class BasicComponent extends BaseComponent {
	public void initialize() {
		expose("exposedInBoth", "both from component");
		expose("exposedInComponent", "hello from component");
	}
}
