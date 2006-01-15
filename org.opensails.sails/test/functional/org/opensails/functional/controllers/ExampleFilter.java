package org.opensails.functional.controllers;

import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionFilter;

public class ExampleFilter implements IActionFilter<FilterTestController> {
	public void afterAction(IAction action, FilterTestController context) {}

	public boolean beforeAction(IAction action, FilterTestController context) {
		context.filterContributions.add("ExampleFilter#beforeAction");
		return true;
	}
}
