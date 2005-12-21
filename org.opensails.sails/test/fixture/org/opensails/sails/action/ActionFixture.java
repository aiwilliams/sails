package org.opensails.sails.action;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.action.oem.Action;
import org.opensails.sails.action.oem.ActionParameterList;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.event.oem.ShamEvent;

public class ActionFixture {
	public static IAction adapters(String actionName, Class<? extends IControllerImpl> controllerImpl, IAdapterResolver adapterResolver) {
		return new Action(actionName, controllerImpl, adapterResolver);
	}

	public static Action create() {
		return defaultAdapters("fromCreateOnActionFixture", null);
	}

	public static Action defaultAdapters(String actionName, Class<? extends IControllerImpl> controllerImpl) {
		return new Action(actionName, controllerImpl, new AdapterResolver());
	}

	public static IActionResult execute(Action action, IControllerImpl controller) {
		return execute(action, controller, ArrayUtils.EMPTY_STRING_ARRAY);
	}

	public static IActionResult execute(Action action, IControllerImpl controller, String[] unadaptedArguments) {
		ShamEvent event = SailsEventFixture.sham();
		AdapterResolver resolver = new AdapterResolver();
		if (controller != null) controller.setEventContext(event, new Controller(controller.getClass(), resolver));
		IActionResult result = action.execute(event, controller, new ActionParameterList(unadaptedArguments, new ContainerAdapterResolver(resolver, event.getContainer())));
		return result;
	}
}
