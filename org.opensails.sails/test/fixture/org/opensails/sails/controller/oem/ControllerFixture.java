package org.opensails.sails.controller.oem;

import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;

public class ControllerFixture {
	public static Controller create() {
		return defaultAdapters();
	}

	public static <I extends IControllerImpl> Controller<I> defaultAdapters() {
		return new Controller<I>(null, new AdapterResolver());
	}

	public static <I extends IControllerImpl> Controller<I> defaultAdapters(Class<I> controllerImpl) {
		return new Controller<I>(controllerImpl, new AdapterResolver());
	}

	public static Controller defaultAdapters(final IControllerImpl controllerImpl) {
		return new Controller<IControllerImpl>((Class<IControllerImpl>) controllerImpl.getClass(), new AdapterResolver()) {
			@Override
			protected IControllerImpl createInstance(ISailsEvent event, Class impl) {
				return controllerImpl;
			}
		};
	}
}
