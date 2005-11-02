package org.opensails.sails.controller.oem;

import java.util.Map;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.oem.ExceptionEvent;

/**
 * DO NOT SORT THIS FILE. It is imperative to validate the behavior of
 * {@link org.opensails.sails.controller.oem.Action} - methods are stored there
 * in order of least arguments to most to allow for finding the best action
 * method.
 * 
 * @author aiwilliams
 */
public class ShamController extends BaseController {
	public String actionInvoked;
	public ISailsEvent eventArgumentValue;
	public int intArgumentValue;
	public Map mapArgumentValue;
	public IActionResult resultReturned;

	public void exception(ExceptionEvent event) {
		actionInvoked = "exception(" + ExceptionEvent.class + ")";
		eventArgumentValue = event;
	}

	public IActionResult resultAction() {
		actionInvoked = "resultAction()";
		return resultReturned = ActionResultFixture.template();
	}

	public void voidActionContainerResult() {
		actionInvoked = "voidActionContainerResult()";
		resultReturned = ActionResultFixture.template();
		setResult(resultReturned);
	}

	public void voidActionMap(Map map) {
		actionInvoked = "voidActionMap(Map)";
		mapArgumentValue = map;
	}

	public void voidActionMultiple() {
		actionInvoked = "voidActionMultiple()";
	}

	public void voidActionMultiple(int one) {
		intArgumentValue = one;
		actionInvoked = "voidActionMultiple(int)";
	}

	public void voidActionMultiple(String one, Object two) {
		actionInvoked = "voidActionMultiple(String, Object)";
	}

	public void voidActionNoParams() {
		actionInvoked = "voidActionNoParams()";
	}

	public void voidActionParams(String one) {
		actionInvoked = "voidActionParams(String)";
	}
}
