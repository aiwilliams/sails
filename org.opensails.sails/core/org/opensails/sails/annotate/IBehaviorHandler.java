package org.opensails.sails.annotate;

import java.lang.annotation.Annotation;

import org.opensails.sails.RequestContainer;
import org.opensails.sails.action.oem.ActionInvocation;

/**
 * Defines the actual code behind a Behavior declaration. These are created and
 * called during the lifecycle of an action invocation.
 * <p>
 * There is only one instance per request, across annotations that reference the
 * handler type. The handler is instantiated using the {@link RequestContainer}
 * of the current event. This allows your handler to have dependencies injected.
 * 
 * @author aiwilliams
 */
public interface IBehaviorHandler<B extends Annotation> {
	boolean STOP_ADDING_BEHAVIORS = false;
	boolean CONTINUE_ADDING_BEHAVIORS = true;

	boolean PREVENT_ACTION_EXECUTION = false;
	boolean ALLOW_ACTION_EXECUTION = true;

	/**
	 * Invoked for each Behavior declaration on an IEventProcessingContext.
	 * <p>
	 * The AnnotationContext is not smart enough to know when to stop calling
	 * add as it works through the heirarchy of the annotated class. Consider
	 * the Layout Behavior. If a Layout is declared on an action method, there
	 * is no need to even consider other Layout annotations. The processor
	 * cannot know this in itself. Therefore, if add() answers false, the
	 * processor will stop processing annotations declared at broader scopes
	 * (class or superclass).
	 * <p>
	 * Consider returning one of {@link #CONTINUE_ADDING_BEHAVIORS} or
	 * {@link #STOP_ADDING_BEHAVIORS} to clarify your code.
	 * 
	 * @param instance
	 * @return false if the AnnotationContext should stop processing equivelant
	 *         Behaviors up the heirarchy
	 */
	boolean add(BehaviorInstance<B> instance);

	/**
	 * Called immediately after an action has been invoked
	 * 
	 * @param invocation on which the action has completed
	 */
	void afterAction(ActionInvocation invocation);

	/**
	 * Called immediately before an action gets invoked.
	 * <p>
	 * If any handler answers false, the action method will not be invoked. All
	 * other handlers will still be invoked. The order of handler invocation is
	 * undefined.
	 * <p>
	 * Consider returning one of {@link #PREVENT_ACTION_EXECUTION} or
	 * {@value #ALLOW_ACTION_EXECUTION} to clarify your code.
	 * 
	 * @param invocation
	 * @return false if the action code should not be executed
	 */
	boolean beforeAction(ActionInvocation invocation);
}
