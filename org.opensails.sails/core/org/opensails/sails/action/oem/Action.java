package org.opensails.sails.action.oem;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.opensails.sails.RequestContainer;
import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionListener;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.annotate.Behavior;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.IBehaviorHandler;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.util.ClassHelper;

/**
 * An Action as seen by the Sails framework.
 * <p>
 * Actions are designed to be cacheable. They should not maintain references to
 * anything related to a specific event. They must remain thread-safe.
 * 
 * @author aiwilliams
 */
public class Action implements IAction {
	protected static final BehaviorInstance[] EMPTY_BEHAVIOR_INSTANCES = new BehaviorInstance[0];

	protected final Method[] actionMethods;
	protected final IAdapterResolver adapterResolver;
	protected final Class<? extends IEventProcessingContext> contextClass;
	protected final String name;

	public Action(String name, Class<? extends IEventProcessingContext> contextClass, IAdapterResolver adapterResolver) {
		this.name = name;
		this.contextClass = contextClass;
		this.adapterResolver = adapterResolver;
		this.actionMethods = ClassHelper.methodsNamedInHeirarchy(contextClass, name);
	}

	public IActionResult execute(ActionInvocation invocation) {
		beginExecution(invocation);
		setFields(invocation);
		if (beforeBehaviors(invocation)) invocation.invoke();
		else invocation.result = invocation.getContext().getActionResult();
		afterBehaviors(invocation);
		if (!invocation.hasResult()) setDefaultResult(invocation);
		registerResult(invocation);
		endExecution(invocation);
		return invocation.result;
	}

	@SuppressWarnings("unchecked")
	protected void setFields(ActionInvocation invocation) {
		IEventProcessingContext context = invocation.getContext();
		if (context == null) return;
		for (Field field : context.getClass().getFields()) {
			String formField = invocation.getFormField(field.getName());
			if (formField != null) {
				IAdapter adapter = adapterResolver.resolve(field.getType(), invocation.getContainer());
				ClassHelper.writeField(context, field, adapter.forModel(field.getType(), formField));
			}
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return contextClass + "#" + name;
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
