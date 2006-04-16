package org.opensails.sails.configurator.oem;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.component.ComponentContainer;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.viento.IBinding;

public class DefaultEventConfigurator implements IEventConfigurator {

	public void configure(ISailsEvent event, ComponentContainer componentContainer) {}

	public void configure(ISailsEvent event, IBinding binding) {}

	public void configure(ISailsEvent event, IEventContextContainer eventContainer) {}

	public void configure(ISailsEvent event, RequestContainer requestContainer) {}

}
