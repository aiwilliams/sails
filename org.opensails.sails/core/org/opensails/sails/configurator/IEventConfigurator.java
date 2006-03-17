package org.opensails.sails.configurator;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.event.ISailsEvent;

public interface IEventConfigurator {
	/**
	 * Called every time an event is dispatched
	 * 
	 * @param event
	 * @param eventContainer convenience, event.getContainer() == eventContainer
	 */
	void configure(ISailsEvent event, IEventContextContainer eventContainer);

}
