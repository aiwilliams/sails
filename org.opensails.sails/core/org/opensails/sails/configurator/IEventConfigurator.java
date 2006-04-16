package org.opensails.sails.configurator;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.component.ComponentContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.viento.IBinding;

/**
 * Called upon for various event happenings.
 * 
 * @author aiwilliams
 */
public interface IEventConfigurator {
	/**
	 * Invoked just after an IBinding is created. Override to extend the binding
	 * for every event.
	 * 
	 * @param event
	 * @param binding
	 */
	public void configure(ISailsEvent event, IBinding binding);

	/**
	 * Called only when a ComponentContainer is created.
	 * 
	 * @param event
	 * @param componentContainer
	 */
	void configure(ISailsEvent event, ComponentContainer componentContainer);

	/**
	 * Called for all types of event container. Implement this if you want to
	 * configure containers for both a Controller and Component.
	 * 
	 * @param event
	 * @param eventContainer convenience, event.getContainer() == eventContainer
	 */
	void configure(ISailsEvent event, IEventContextContainer eventContainer);

	/**
	 * Called only when a RequestContainer is created.
	 * 
	 * @param event
	 * @param requestContainer
	 */
	void configure(ISailsEvent event, RequestContainer requestContainer);
}
