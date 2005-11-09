package org.opensails.sails.controller.oem;

import org.opensails.sails.template.Layout;

@Layout("classLayout")
public class ShamControllerLayouts extends ShamController {
	@Layout("actionLayout")
	public void actionLayout() {}

	public void classLayout() {}

	@Layout("actionLayout")
	public void layoutNoneMethod() {
		layout(null);
	}

	@Layout("actionLayout")
	public void methodLayout() {
		layout("methodLayout");
	}

	public void notTemplateResult() {
		renderString("so the result isnt template");
	}
}
