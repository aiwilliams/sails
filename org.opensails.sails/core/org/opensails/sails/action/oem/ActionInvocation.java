package org.opensails.sails.action.oem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.opensails.sails.RequestContainer;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionParameterList;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.IBehaviorHandler;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.util.ClassHelper;

/**
 * State related to an Action invocation.
 * <p>
 * An ActionInvocation cannot be re-used. It is purposefully designed to not
 * know how to obtain it's code or context (controller/component).
 * 
 * @author Adam 'Programmer' Williams
 */
public class ActionInvocation {
	public Method code;
	public final ISailsEvent event;
	public final IActionParameterList parameters;
	public IActionResult result;

	protected final IAction action;
	protected final IEventProcessingContext context;
	protected final Map<Class<? extends IBehaviorHandler>, IBehaviorHandler> handlers;

	public ActionInvocation(ISailsEvent event, IAction action, IActionParameterList parameters, IEventProcessingContext context) {
		this.event = event;
		this.action = action;
		this.context = context;
		this.parameters = parameters;
		this.handlers = new LinkedHashMap<Class<? extends IBehaviorHandler>, IBehaviorHandler>();
	}

	public IAction getAction() {
		return action;
	}

	public String getActionName() {
		return action.getName();
	}

	public RequestContainer getContainer() {
		return event.getContainer();
	}

	public IEventProcessingContext<?> getContext() {
		return context;
	}

	public Class<? extends IEventProcessingContext> getContextClass() {
		return context.getClass();
	}

	/**
	 * @param behavior
	 * @return the active handler for the behavior. This will be the same
	 *         instance where the behavior answers equals() to instances that
	 *         have been handled before.
	 */
	@SuppressWarnings("unchecked")
	public IBehaviorHandler<?> getHandler(BehaviorInstance behavior) {
		IBehaviorHandler handler = handlers.get(behavior.getBehaviorHandlerClass());
		if (handler == null) {
			handler = (IBehaviorHandler) ClassHelper.instantiate(behavior.getBehaviorHandlerClass());
			handlers.put(behavior.getBehaviorHandlerClass(), handler);
		}
		return handler;
	}

	public Set<IBehaviorHandler> getHandlers() {
		return new HashSet<IBehaviorHandler>(handlers.values());
	}

	public boolean hasCode() {
		return code != null;
	}

	public boolean hasContext() {
		return context != null;
	}

	public boolean hasResult() {
		return result != null;
	}

	public void invoke() {
		if (!hasCode()) return;

		IEventProcessingContext<?> context = getContext();
		Object[] actionArguments = parameters();
		try {
			Object returnValue = code.invoke(context, actionArguments);
			if (isActionResult(returnValue)) result = (IActionResult) returnValue;
			else result = context.getActionResult();
		} catch (IllegalArgumentException e) {
			throw new ParameterMismatchException(event, code, actionArguments);
		} catch (IllegalAccessException e) {
			throw new SailsException("Action methods on an ActionMethodController must be public.", e);
		} catch (InvocationTargetException e) {
			throw new SailsException("An exception [" + e.getCause().getClass() + "] occurred in the action " + code, e.getCause());
		}
	}

	public Object[] parameters() {
		return parameters.objects(code.getParameterTypes());
	}

	public void setResult(TemplateActionResult actionResult) {
		result = actionResult;
		getContext().setResult(actionResult);
	}

	private boolean isActionResult(Object returnValue) {
		return returnValue != null && returnValue instanceof IActionResult;
	}
}
