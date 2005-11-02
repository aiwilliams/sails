package org.opensails.sails.controller.oem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.SailsException;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IControllerImpl;

public class Action implements IAction {
	protected static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

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
	 * Executes this action without adapting the parameters.
	 * 
	 * @param event TODO
	 * @param implementationInstance
	 * 
	 * @param parameters, which will not be adapted
	 * @return the result
	 */
	public IActionResult execute(ISailsEvent event, IControllerImpl implementationInstance, Object[] parameters) {
		if (implementationInstance == null) {
			if (controllerImplementation != null) throw new NullPointerException("This action must be executed against a non null implementation of "
					+ controllerImplementation);
			else return defaultActionResult(event, implementationInstance);
		}

		if (!controllerImplementation.isAssignableFrom(implementationInstance.getClass())) throw new IllegalArgumentException("This action is not for "
				+ implementationInstance.getClass());

		Method actionMethod = methodHavingArgCount(parameters.length);
		if (actionMethod == null) return defaultActionResult(event, implementationInstance);
		return executeMethod(event, implementationInstance, actionMethod, parameters);
	}

	/**
	 * Adapts the unadaptedParameters, then invokes the action.
	 * 
	 * @param event TODO
	 * @param implementationInstance
	 * @param unadaptedParameters
	 * 
	 * @return the result
	 */
	public IActionResult execute(ISailsEvent event, IControllerImpl implementationInstance, String[] unadaptedParameters) {
		if (implementationInstance == null) {
			if (controllerImplementation != null) throw new NullPointerException("This action must be executed against a non null implementation of "
					+ controllerImplementation);
			else return defaultActionResult(event, implementationInstance);
		}

		if (!controllerImplementation.isAssignableFrom(implementationInstance.getClass())) throw new IllegalArgumentException("This action is not for "
				+ implementationInstance.getClass());

		Method actionMethod = methodHavingArgCount(unadaptedParameters.length);
		if (actionMethod == null) return defaultActionResult(event, implementationInstance);
		Object[] actionArguments = adaptParameters(unadaptedParameters, actionMethod.getParameterTypes(), implementationInstance.getContainer());
		return executeMethod(event, implementationInstance, actionMethod, actionArguments);
	}

	/* (non-Javadoc)
	 * @see org.opensails.sails.controller.oem.IAction#getParameterTypes(int)
	 */
	public Class<?>[] getParameterTypes(int numberOfArguments) {
		return methodHavingArgCount(numberOfArguments).getParameterTypes();
	}

	@Override
	public String toString() {
		return controllerImplementation + "#" + name;
	}

	protected Object[] adaptParameters(String[] unadaptedParameters, Class<?>[] targetTypes, ScopedContainer container) {
		Object[] adaptedParameters = new Object[targetTypes.length];
		for (int i = 0; i < targetTypes.length; i++) {
			IAdapter adapter = adapterResolver.resolve(targetTypes[i], container);
			Object adapted = adapter.forModel(targetTypes[i], unadaptedParameters[i]);
			adaptedParameters[i] = adapted;
		}
		return adaptedParameters;
	}

	protected IActionResult defaultActionResult(ISailsEvent event, IControllerImpl implementationInstance) {
		return new TemplateActionResult(event);
	}

	protected IActionResult executeMethod(ISailsEvent event, IControllerImpl implementationInstance, Method actionMethod, Object[] actionArguments) {
		try {
			Object result = actionMethod.invoke(implementationInstance, actionArguments);
			if (result != null && result instanceof IActionResult) return (IActionResult) result;
			IActionResult resultInContainer = implementationInstance.getContainer().instance(IActionResult.class);
			if (resultInContainer != null) return resultInContainer;
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

	protected Method methodHavingArgCount(int i) {
		for (Method method : actionMethods)
			if (method.getParameterTypes().length <= i) return method;
		return null;
	}
}
