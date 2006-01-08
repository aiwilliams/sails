package org.opensails.functional.controllers;

import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.action.ActionFilters;
import org.opensails.sails.action.BeforeFilters;
import org.opensails.sails.controller.oem.BaseController;

@BeforeFilters(actions = @ActionFilters(filters = ExampleFilter.class))
public class FilterTestController extends BaseController {
	public List<Object> filterContributions = new ArrayList<Object>();

	public void filteredBefore() {
		expose("filterContributions", filterContributions);
	}
}
