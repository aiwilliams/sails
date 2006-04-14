package org.opensails.functional.controllers;

import org.opensails.sails.template.Layout;
import org.opensails.sails.template.NoLayout;

public class LayoutTestController extends LayoutSuperController {
	@Layout("layoutTest/layout")
	public void index() {}

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

	@NoLayout
	public void seven() {}

	public void six() {
		layout(null);
	}

	@Override
	public void two() {}
}
