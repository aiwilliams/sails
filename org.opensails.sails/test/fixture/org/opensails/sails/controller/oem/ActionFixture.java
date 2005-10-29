package org.opensails.sails.controller.oem;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.oem.AdapterResolver;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.oem.ShamEvent;

public class ActionFixture {
	public static Action adapters(String actionName, Class<? extends IController> controllerImpl, IAdapterResolver adapterResolver) {
		return new Action(actionName, controllerImpl, adapterResolver);
	}

	public static Action defaultAdapters(String actionName, Class<? extends IController> controllerImpl) {
		return new Action(actionName, controllerImpl, new AdapterResolver());
	}

	public static IActionResult execute(Action action, IController controller) {
		return execute(action, controller, ArrayUtils.EMPTY_STRING_ARRAY);
	}

	public static IActionResult execute(Action action, IController controller, String[] unadaptedArguments) {
		ShamEvent event = SailsEventFixture.sham();
		if (controller != null) controller.set(event);
		return action.execute(event, controller, unadaptedArguments);
	}
}
