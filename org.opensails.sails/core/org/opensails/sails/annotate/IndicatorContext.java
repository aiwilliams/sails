package org.opensails.sails.annotate;

import org.opensails.sails.event.IEventProcessingContext;

public class IndicatorContext {
	/**
	 * @return the class on which the indicators were declared
	 */
	public Class<? extends IEventProcessingContext> context() {
		return null;
	}

	public IndicatorInstance[] instances() {
		return null;
	}
}
