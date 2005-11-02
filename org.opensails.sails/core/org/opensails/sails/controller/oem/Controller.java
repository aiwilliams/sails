package org.opensails.sails.controller.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.oem.ExceptionEvent;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.PostEvent;

public class Controller implements IController {
	protected final Map<String, Action> actions;
	protected final IAdapterResolver adapterResolver;
	protected final Class<? extends IControllerImpl> controllerImplementation;

	public Controller(Class<? extends IControllerImpl> controller, IAdapterResolver adapterResolver) {
		this.controllerImplementation = controller;
		this.adapterResolver = adapterResolver;
		this.actions = new HashMap<String, Action>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opensails.sails.controller.oem.IControllerMeta#getAction(java.lang.String)
	 */
	public Action getAction(String name) {
		Action action = actions.get(name);
		if (action == null) {
			action = new Action(name, controllerImplementation, adapterResolver);
			actions.put(name, action);
		}
		return action;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opensails.sails.controller.oem.IControllerMeta#getImplementation()
	 */
	public Class<? extends IControllerImpl> getImplementation() {
		return controllerImplementation;
	}

	public IActionResult process(ExceptionEvent event) {
		IControllerImpl controller = createInstance(event);
		Action action = getAction(event.getActionName());
		// TODO: Pass the event.getOriginatingEvent().getActionParameters() -
		// these should not be adapted
		return action.execute(event, controller, new Object[] { event });
	}

	public IActionResult process(GetEvent event) {
		return process((ISailsEvent) event);
	}

	public IActionResult process(ISailsEvent event) {
		IControllerImpl controller = createInstance(event);
		Action action = getAction(event.getActionName());
		return action.execute(event, controller, event.getActionParameters());
	}

	public IActionResult process(PostEvent event) {
		return process((ISailsEvent) event);
	}

	protected IControllerImpl createInstance(ISailsEvent event) {
		if (controllerImplementation == null) return null;
		ScopedContainer container = event.getContainer();
		IControllerImpl instance = container.instance(controllerImplementation, controllerImplementation);
		container.register(IControllerImpl.class, instance);
		instance.set(event, this);
		return instance;
	}
}
