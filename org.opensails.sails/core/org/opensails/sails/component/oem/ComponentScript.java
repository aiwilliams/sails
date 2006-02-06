package org.opensails.sails.component.oem;

import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.html.IInlineContent;
import org.opensails.sails.html.Script;

public class ComponentScript extends Script implements IInlineContent {
	private final IComponentImpl component;

	public ComponentScript(IComponentImpl component) {
		this.inlineContent = this;
		this.component = component;
	}

	public String render() {
		return "hi";
	}
}
