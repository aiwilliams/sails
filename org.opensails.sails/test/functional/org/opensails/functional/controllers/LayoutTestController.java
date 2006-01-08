package org.opensails.functional.controllers;

import org.opensails.sails.template.Layout;

public class LayoutTestController extends LayoutSuperController {
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

	@Override
	@Layout("oneLayout")
	public void one() {}

	@Override
	public void two() {}
}
