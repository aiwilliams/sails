package org.opensails.sails.controller.oem;

import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;

public class ControllerFixture {
	public static Controller create() {
		return defaultAdapters();
	}

	public static Controller defaultAdapters() {
		return new Controller(null, new AdapterResolver());
	}

	public static Controller defaultAdapters(Class<? extends IControllerImpl> controllerImpl) {
		return new Controller(controllerImpl, new AdapterResolver());
	}

	public static <I extends IControllerImpl> Controller defaultAdapters(final I controllerImpl) {
		return new Controller(controllerImpl.getClass(), new AdapterResolver()) {
			@Override
			protected I createInstance(ISailsEvent event, Class impl) {
				return controllerImpl;
			}
		};
	}
}
