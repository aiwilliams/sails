package org.opensails.sails.controller.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.Action;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.NoImplementationException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.ExceptionEvent;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.PostEvent;
import org.opensails.sails.template.Layout;

public class Controller implements IController {
	protected final Map<String, Action> actions;
	protected final IAdapterResolver adapterResolver;
	protected final Class<? extends IControllerImpl> controllerImplementation;

	public Controller(Class<? extends IControllerImpl> controller, IAdapterResolver adapterResolver) {
		this.controllerImplementation = controller;
		this.adapterResolver = adapterResolver;
		this.actions = new HashMap<String, Action>();
	}

	public IControllerImpl createInstance(ISailsEvent event) throws NoImplementationException {
		if (!hasImplementation()) throw new NoImplementationException(this);
		return createInstanceOrNull(event);
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

	public boolean hasImplementation() {
		return controllerImplementation != null;
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
		IControllerImpl controller = createInstanceOrNull(event);
		Action action = getAction(event.getActionName());
		IActionResult actionResult = action.execute(event, controller, event.getActionParameters());
		if (actionResult instanceof TemplateActionResult && !((TemplateActionResult) actionResult).hasLayoutBeenSet() && controller != null
				&& controller.getClass().isAnnotationPresent(Layout.class)) {
			Layout layout = controller.getClass().getAnnotation(Layout.class);
			((TemplateActionResult) actionResult).setLayout(layout.value());
		}
		return actionResult;
	}

	public IActionResult process(PostEvent event) {
		return process((ISailsEvent) event);
	}

	private IControllerImpl createInstanceOrNull(ISailsEvent event) {
		if (!hasImplementation()) return null;
		IControllerImpl instance = createInstance(event, controllerImplementation);
		instance.setEventContext(event, this);
		return instance;
	}

	protected IControllerImpl createInstance(ISailsEvent event, Class<? extends IControllerImpl> controllerImpl) {
		return event.getContainer().create(controllerImplementation, event);
	}
}
