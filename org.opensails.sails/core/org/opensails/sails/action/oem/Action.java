package org.opensails.sails.action.oem;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

import org.opensails.sails.*;
import org.opensails.sails.action.*;
import org.opensails.sails.adapter.*;
import org.opensails.sails.annotate.*;
import org.opensails.sails.event.*;
import org.opensails.sails.util.*;

/**
 * An Action as seen by the Sails framework.
 * <p>
 * Actions are designed to be cacheable. They should not maintain references to
 * anything related to a specific event. They must remain thread-safe.
 * 
 * @author Adam 'Programmer' Williams
 */
public class Action implements IAction {
	protected static final BehaviorInstance[] EMPTY_BEHAVIOR_INSTANCES = new BehaviorInstance[0];

	protected final Method[] actionMethods;
	protected final IAdapterResolver adapterResolver;
	protected final Class<? extends IEventProcessingContext> processor;
	protected final String name;

	public Action(String name, Class<? extends IEventProcessingContext> processor, IAdapterResolver adapterResolver) {
		this.name = name;
		this.processor = processor;
		this.adapterResolver = adapterResolver;
		this.actionMethods = ClassHelper.methodsNamedInHeirarchy(processor, name);
	}

	public IActionResult execute(ActionInvocation invocation) {
		beginExecution(invocation);
		if (beforeBehaviors(invocation)) invocation.invoke();
		else invocation.result = invocation.getContext().getActionResult();
		afterBehaviors(invocation);
		if (!invocation.hasResult()) setDefaultResult(invocation);
		registerResult(invocation);
		endExecution(invocation);
		return invocation.result;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return processor + "#" + name;
	}

	private BehaviorInstance[] allBehaviors(ActionInvocation invocation) {
		if (!invocation.hasContext()) return EMPTY_BEHAVIOR_INSTANCES;

		List<BehaviorInstance> instances = new ArrayList<BehaviorInstance>();

		if (invocation.hasCode()) collectBehaviorsWhenActionCode(instances, invocation);
		else collectBehaviorsWhenNoActionCode(instances, invocation);
		return instances.toArray(new BehaviorInstance[instances.size()]);
	}

	@SuppressWarnings("unchecked")
	private void collectActionBehaviors(List<BehaviorInstance> instances, Method method) {
		for (Annotation annotation : method.getAnnotations())
			if (annotation.annotationType().isAnnotationPresent(Behavior.class)) instances.add(new BehaviorInstance(annotation, ElementType.METHOD));
	}

	private void collectBehaviorsWhenActionCode(List<BehaviorInstance> instances, ActionInvocation invocation) {
		Set<Class<?>> processedControllers = new HashSet<Class<?>>();
		Method action = invocation.code;
		do {
			collectActionBehaviors(instances, action);
			Class<?> controller = action.getDeclaringClass();
			if (!processedControllers.contains(controller)) collectControllerBehaviors(instances, controller);
			processedControllers.add(controller);

			Method overriddenAction = overriddenAction(controller, action);
			if (overriddenAction != null) {
				action = overriddenAction;
			} else {
				action = null;
			}
		} while (action != null);
	}

	private void collectBehaviorsWhenNoActionCode(List<BehaviorInstance> instances, ActionInvocation invocation) {
		Class controller = invocation.getContextClass();
		do {
			collectControllerBehaviors(instances, controller);
			controller = controller.getSuperclass();
		} while (controller != Object.class);
	}

	@SuppressWarnings("unchecked")
	private void collectControllerBehaviors(List<BehaviorInstance> instances, Class type) {
		for (Annotation annotation : type.getAnnotations())
			if (annotation.annotationType().isAnnotationPresent(Behavior.class)) instances.add(new BehaviorInstance(annotation, ElementType.TYPE));
	}

	@SuppressWarnings("unchecked")
	private void initializeHandlers(ActionInvocation invocation) {
		Set<IBehaviorHandler> satisfiedHandlers = new LinkedHashSet<IBehaviorHandler>();
		BehaviorInstance[] behaviors = allBehaviors(invocation);
		for (BehaviorInstance behavior : behaviors) {
			IBehaviorHandler<?> handler = invocation.getHandler(behavior);
			if (!satisfiedHandlers.contains(handler) && !handler.add(behavior)) satisfiedHandlers.add(handler);
		}
	}

	private Method overriddenAction(Class controller, Method action) {
		Class<?> controllerSuperclass = controller.getSuperclass();
		if (controllerSuperclass == Object.class) return null;
		try {
			return controllerSuperclass.getMethod(action.getName(), (Class[]) action.getParameterTypes());
		} catch (Exception possibleAndIgnored) {
			return null;
		}
	}

	protected void afterBehaviors(ActionInvocation invocation) {
		for (IBehaviorHandler handler : invocation.getHandlers())
			handler.afterAction(invocation);
	}

	protected boolean beforeBehaviors(ActionInvocation invocation) {
		for (IBehaviorHandler handler : invocation.getHandlers())
			if (!handler.beforeAction(invocation)) return false;
		return true;
	}

	protected void beginExecution(ActionInvocation invocation) {
		getActionListeners(invocation.event).beginExecution(this);
		invocation.code = methodHavingArgCount(invocation.parameters.size());
		initializeHandlers(invocation);
	}

	protected IActionResult defaultActionResult(ISailsEvent event) {
		return new TemplateActionResult(event);
	}

	protected void endExecution(ActionInvocation invocation) {
		getActionListeners(invocation.event).endExecution(this);
	}

	protected IActionListener getActionListeners(ISailsEvent event) {
		return event.getApplication().getContainer().broadcast(IActionListener.class, false);
	}

	protected Method methodHavingArgCount(int i) {
		for (Method method : actionMethods)
			if (method.getParameterTypes().length <= i) return method;
		return null;
	}

	protected void registerResult(ActionInvocation invocation) {
		RequestContainer container = invocation.getContainer();
		container.register(IActionResult.class, invocation.result);
		container.register(invocation.result);
	}

	protected void setDefaultResult(ActionInvocation invocation) {
		invocation.result = defaultActionResult(invocation.event);
	}
}
