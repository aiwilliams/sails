package org.opensails.dock.components;

import java.util.List;

import org.opensails.sails.component.oem.BaseComponent;

public class MultiselectComponent extends BaseComponent {
	public void initialize(String name, List<String> options) {
		expose("id", name.replace(' ', '_'));
		expose("name", name);
		expose("options", options);
	}
}
