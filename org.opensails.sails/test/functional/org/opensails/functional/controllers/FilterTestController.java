package org.opensails.functional.controllers;

import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.action.ActionFilters;
import org.opensails.sails.action.ActionMethods;
import org.opensails.sails.action.BeforeFilter;
import org.opensails.sails.action.BeforeFilters;
import org.opensails.sails.controller.oem.BaseController;

@BeforeFilters(actions = @ActionFilters(filters = ExampleFilter.class, except = "filteredExcluded"), methods = @ActionMethods(methods = "onlyFilter", only = "filteredOnly"))
public class FilterTestController extends BaseController {
	public List<Object> filterContributions = new ArrayList<Object>();

	@BeforeFilters(methods = @ActionMethods(methods = "beforeMethod"), actions = @ActionFilters(filters = ExampleFilterTwo.class))
	public void filteredBefore() {
		expose("filterContributions", filterContributions);
		renderTemplate("filteredBefore");
	}

	public void filteredExcluded() {
		filteredBefore();
	}

	public void filteredOnly() {
		filteredBefore();
	}

	@BeforeFilter(method = "terminatingFilter")
	public void terminated() {
		throw new RuntimeException("Should not get here");
	}

	protected void beforeMethod() {
		filterContributions.add("FilterTestController#beforeMethod");
	}

	protected void onlyFilter() {
		filterContributions.add("FilterTestController#onlyFilter");
	}

	protected boolean terminatingFilter() {
		filterContributions.add("FilterTestController#terminating");
		filteredBefore();
		return false;
	}
}
