package org.opensails.sails.controller.oem;

import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.AbstractActionEventProcessor;

public class Controller<I extends IControllerImpl> extends AbstractActionEventProcessor<I> implements IController {
	public Controller(Class<I> controller, IAdapterResolver adapterResolver) {
		super(controller, adapterResolver);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected I createNullInstance(ISailsEvent event) {
		return (I) new NullController();
	}
}
