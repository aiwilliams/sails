package org.opensails.functional.controllers;

import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.template.Layout;

/**
 * The 'super class' of a Controller that declares a layout.
 */
@Layout("classSuperLayout")
public class LayoutSuperController extends BaseController {
	public void one() {}

	public void three() {}
	
	public void five() {
		renderTemplate("three").setLayout("renderTemplateLayout");
	}

	@Layout("twoSuperLayout")
	public void two() {}
}
