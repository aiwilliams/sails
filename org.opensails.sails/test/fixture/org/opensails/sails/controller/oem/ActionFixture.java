package org.opensails.sails.controller.oem;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IAction;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.oem.AdapterResolver;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.oem.ShamEvent;

public class ActionFixture {
	public static IAction adapters(String actionName, Class<? extends IControllerImpl> controllerImpl, IAdapterResolver adapterResolver) {
		return new Action(actionName, controllerImpl, adapterResolver);
	}

	public static Action defaultAdapters(String actionName, Class<? extends IControllerImpl> controllerImpl) {
		return new Action(actionName, controllerImpl, new AdapterResolver());
	}

	public static IActionResult execute(Action action, IControllerImpl controller) {
		return execute(action, controller, ArrayUtils.EMPTY_STRING_ARRAY);
	}

	public static IActionResult execute(Action action, IControllerImpl controller, String[] unadaptedArguments) {
		ShamEvent event = SailsEventFixture.sham();
		if (controller != null) controller.set(event, new Controller(controller.getClass(), new AdapterResolver()));
		return action.execute(event, controller, unadaptedArguments);
	}
}
