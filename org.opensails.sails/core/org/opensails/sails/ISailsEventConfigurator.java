package org.opensails.sails;

public interface ISailsEventConfigurator {
	/**
	 * Called every time an event is dispatched
	 * 
	 * @param event
	 * @param eventContainer convenience, event.getContainer() == eventContainer
	 */
	void configure(ISailsEvent event, RequestContainer eventContainer);

}
