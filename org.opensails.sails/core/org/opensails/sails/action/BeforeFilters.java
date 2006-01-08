package org.opensails.sails.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opensails.sails.action.oem.ActionFilterHandler;
import org.opensails.sails.annotate.Behavior;

/**
 * Allows an IEventProcessingContext to declare that filters should be invoked
 * before an action is executed.
 * <p>
 * Filters, method or other, will be invoked in the order that they are
 * declared. If a beforeAction returns false or, in the case of a method filter,
 * a non-null IActionResult, further processing will cease. A filter may render,
 * redirect, whatever - just remember that returning false will prevent the
 * action code from being invoked.
 * <p>
 * When {@link #actions()} are used, ActionFilterHandler will use the same
 * instances in both the before and after events. You can capture state, if you
 * like, in the beforeAction and it will be available in the afterAction.
 * 
 * @author Adam 'Programmer' Williams
 */
@Documented
@Behavior(behavior = ActionFilterHandler.class)
@Target( { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeFilters {
	ActionFilters[] actions() default @ActionFilters(filters = IActionFilter.class);

	ActionMethods[] methods() default @ActionMethods(methods = "");
}
