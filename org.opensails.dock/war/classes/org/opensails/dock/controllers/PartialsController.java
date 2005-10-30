package org.opensails.dock.controllers;

import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.controller.oem.PartialActionResult;

public class PartialsController extends BaseController {
	public PartialActionResult renderPartial() {
		return new PartialActionResult(event);
	}

	public PartialActionResult renderOtherPartial() {
		return new PartialActionResult(event, "otherPartial");
	}
}
