package org.opensails.sails.action;

/**
 * Instances in the containers that implement this will be notified of certain
 * action events.
 */
public interface IActionListener {
	/**
	 * Invoked at the start of the execution of an action, preceeding all
	 * before-behaviors.
	 * 
	 * @param action
	 */
	void beginExecution(IAction action);

	/**
	 * Invoked at the end of the execution of an action, proceeding all
	 * after-behaviors, before any result processing. The IActionResult is
	 * available in the event container.
	 * 
	 * @param action
	 */
	void endExecution(IAction action);
}
