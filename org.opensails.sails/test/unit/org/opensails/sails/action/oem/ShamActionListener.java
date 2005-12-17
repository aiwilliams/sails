/**
 * 
 */
package org.opensails.sails.action.oem;

import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionListener;

public class ShamActionListener implements IActionListener {
	public List<String> notifications = new ArrayList<String>();

	public void beginExecution(IAction action) {
		notifications.add("beginExecution");
	}

	public void endExecution(IAction action) {
		notifications.add("endExecution");
	}
}