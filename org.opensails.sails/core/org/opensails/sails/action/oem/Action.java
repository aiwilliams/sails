package org.opensails.sails.action.oem;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.opensails.sails.RequestContainer;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionListener;
import org.opensails.sails.action.IActionParameterList;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.template.Layout;

public class Action implements IAction {
	protected static final Method[] EMPTY_METHOD_ARRAY = new Method[0];
	protected static final Annotation[] NO_ANNOTATIONS = new Annotation[0];

	protected final Method[] actionMethods;
	protected final IAdapterResolver adapterResolver;
	protected final Class<? extends IControllerImpl> controllerImplementation;
	protected final String name;

	public Action(String name, Class<? extends IControllerImpl> controllerImplementation, IAdapterResolver adapterResolver) {
		this.name = name;
		this.controllerImplementation = controllerImplementation;
		this.adapterResolver = adapterResolver;
		this.actionMethods = findMethods(name);
	}

	/**
	 * Adapts the unadaptedParameters, then invokes the action.
	 * 
	 * @param event
	 * @param implementationInstance
	 * @param parameters
	 * 
	 * @return the result
	 */
	public IActionResult execute(ISailsEvent event, IControllerImpl implementationInstance, IActionParameterList parameters) {
		IActionListener listener = getActionListeners(event);
		listener.beginExecution(this);
		Method actionMethod = methodHavingArgCount(parameters.size());

		IActionResult result = null;
		if (actionMethod != null) {
			Object[] actionArguments = parameters.objects(actionMethod.getParameterTypes());
			result = executeMethod(event, implementationInstance, actionMethod, actionArguments);
			if (result instanceof TemplateActionResult) {
				TemplateActionResult templateResult = (TemplateActionResult) result;
				if (!templateResult.hasLayoutBeenSet()) if (actionMethod.isAnnotationPresent(Layout.class)) templateResult.setLayout(actionMethod.getAnnotation(Layout.class).value());
			}
		} else result = defaultActionResult(event, implementationInstance);

		listener.endExecution(this);
		return finalizeExecution(event, result);
	}

	/**
	 * Executes this action without adapting the parameters.
	 * 
	 * @param event
	 * @param implementationInstance
	 * 
	 * @param parameters, which will not be adapted
	 * @return the result
	 */
	public IActionResult execute(ISailsEvent event, IControllerImpl implementationInstance, Object[] parameters) {
		IActionListener listener = getActionListeners(event);
		listener.beginExecution(this);
		Method actionMethod = methodHavingArgCount(parameters.length);

		IActionResult result = null;
		if (actionMethod == null) result = defaultActionResult(event, implementationInstance);
		else result = executeMethod(event, implementationInstance, actionMethod, parameters);

		listener.endExecution(this);
		return finalizeExecution(event, result);
	}

	public Annotation[] getAnnotations() {
		return actionMethods != null && actionMethods.length > 0 ? actionMethods[0].getAnnotations() : NO_ANNOTATIONS;
	}

	public Class<?>[] getParameterTypes(int numberOfArguments) {
		return methodHavingArgCount(numberOfArguments).getParameterTypes();
	}

	@Override
	public String toString() {
		return controllerImplementation + "#" + name;
	}

	protected IActionResult defaultActionResult(ISailsEvent event, IControllerImpl implementationInstance) {
		return new TemplateActionResult(event);
	}

	protected IActionResult executeMethod(ISailsEvent event, IControllerImpl implementationInstance, Method actionMethod, Object[] actionArguments) {
		try {
			Object result = actionMethod.invoke(implementationInstance, actionArguments);
			if (result != null && result instanceof IActionResult) return (IActionResult) result;
			IActionResult resultFromController = implementationInstance.getActionResult();
			if (resultFromController != null) return resultFromController;
			return defaultActionResult(event, implementationInstance);
		} catch (IllegalArgumentException e) {
			// TODO: Handle illegal argument when invoking action method
			throw new ParameterMismatchException(event, actionMethod, actionArguments);
		} catch (IllegalAccessException e) {
			throw new SailsException("Action methods on an ActionMethodController must be public.", e);
		} catch (InvocationTargetException e) {
			throw new SailsException("An exception [" + e.getCause().getClass() + "] occurred in the action " + actionMethod, e.getCause());
		}
	}

	protected IActionResult finalizeExecution(ISailsEvent event, IActionResult result) {
		RequestContainer container = event.getContainer();
		container.register(IActionResult.class, result);
		container.register(result);
		return result;
	}

	protected Method[] findMethods(String name) {
		if (controllerImplementation == null) return EMPTY_METHOD_ARRAY;

		List<Method> matches = new ArrayList<Method>();
		Method[] declaredMethods = controllerImplementation.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			if (declaredMethods[i].getName().equals(name)) matches.add(declaredMethods[i]);
		}
		Collections.sort(matches, new Comparator<Method>() {
			public int compare(Method o1, Method o2) {
				return o2.getParameterTypes().length - o1.getParameterTypes().length;
			}
		});
		return matches.toArray(new Method[matches.size()]);
	}

	protected IActionListener getActionListeners(ISailsEvent event) {
		return event.getApplication().getContainer().broadcast(IActionListener.class, false);
	}

	protected Method methodHavingArgCount(int i) {
		for (Method method : actionMethods)
			if (method.getParameterTypes().length <= i) return method;
		return null;
	}
}
