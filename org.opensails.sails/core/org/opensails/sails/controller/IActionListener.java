package org.opensails.sails.controller;

public interface IActionListener {
	void beginExecution(IAction action);
	void endExecution(IAction action);
}
