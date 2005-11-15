/**
 * 
 */
package org.opensails.sails.controller.oem;

import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.controller.IAction;
import org.opensails.sails.controller.IActionListener;

public class ShamActionListener implements IActionListener {
	public List<String> notifications = new ArrayList<String>();

	public void beginExecution(IAction action) {
		notifications.add("beginExecution");
	}

	public void endExecution(IAction action) {
		notifications.add("endExecution");
	}
}