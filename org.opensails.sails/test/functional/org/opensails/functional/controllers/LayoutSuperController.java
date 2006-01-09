package org.opensails.functional.controllers;

import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.template.Layout;
import org.opensails.sails.template.NoLayout;

/**
 * The 'super class' of a Controller that declares a layout.
 */
@Layout("classSuperLayout")
public class LayoutSuperController extends BaseController {
	@NoLayout
	public void eight() {}

	public void five() {
		renderTemplate("three").setLayout("renderTemplateLayout");
	}

	public void one() {}

	public void three() {}

	@Layout("twoSuperLayout")
	public void two() {}
}
