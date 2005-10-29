package org.opensails.sails.controller.oem;

import org.opensails.sails.controller.IController;
import org.opensails.sails.oem.AdapterResolver;

public class ControllerFixture {
	public static Controller defaultAdapters() {
		return defaultAdapters(null);
	}

	public static Controller defaultAdapters(Class<? extends IController> controllerImpl) {
		return new Controller(controllerImpl, new AdapterResolver());
	}
}
