package org.opensails.sails.controller.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.oem.AdapterResolver;

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

	public static Controller defaultAdapters(final IControllerImpl controllerImpl) {
		return new Controller(controllerImpl.getClass(), new AdapterResolver()) {
			@Override
			protected IControllerImpl createInstance(ISailsEvent event, Class<? extends IControllerImpl> impl) {
				return controllerImpl;
			}
		};
	}
}
