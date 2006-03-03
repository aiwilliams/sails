package org.opensails.sails.event;

import org.opensails.sails.IEventContextContainer;

public interface ISailsEventConfigurator {
	/**
	 * Called every time an event is dispatched
	 * 
	 * @param event
	 * @param eventContainer convenience, event.getContainer() == eventContainer
	 */
	void configure(ISailsEvent event, IEventContextContainer eventContainer);

}
