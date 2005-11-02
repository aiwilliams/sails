package org.opensails.sails.controller.oem;

import org.opensails.sails.template.Layout;

@Layout("classLayout")
public class ShamControllerLayouts extends ShamController {
	@Layout("actionLayout")
	public void actionLayout() {
		actionInvoked = "actionLayout";
	}

	public void classLayout() {
		actionInvoked = "classLayout()";
	}
}
