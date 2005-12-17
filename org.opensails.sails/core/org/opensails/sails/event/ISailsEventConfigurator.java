package org.opensails.sails.event;

import org.opensails.sails.RequestContainer;

public interface ISailsEventConfigurator {
	/**
	 * Called every time an event is dispatched
	 * 
	 * @param event
	 * @param eventContainer convenience, event.getContainer() == eventContainer
	 */
	void configure(ISailsEvent event, RequestContainer eventContainer);

}
