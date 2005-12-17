package org.opensails.sails.action;

/**
 * Instances in the containers that implement this will be notified of certain
 * action events.
 */
public interface IActionListener {
	void beginExecution(IAction action);

	void endExecution(IAction action);
}
