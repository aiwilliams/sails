package org.opensails.sails.action;

import org.opensails.sails.event.IEventProcessingContext;

public interface IActionFilter<C extends IEventProcessingContext<?>> {
	void afterAction(IAction action, C context);

	boolean beforeAction(IAction action, C context);
}