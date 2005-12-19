package org.opensails.sails.annotate;

import java.lang.annotation.Annotation;

import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.oem.IActionEventProcessor;

/**
 * Defines the actual code behind a Behavior declaration. These are created and
 * called during the lifecycle of an action invocation.
 * <p>
 * There is only one instance per request, accross annotations that reference
 * the handler type.
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IBehaviorHandler<B extends Annotation> {
	void add(BehaviorInstance<B> instance);

	void afterAction(IEventProcessingContext<? extends IActionEventProcessor> context);

	void beforeAction(IEventProcessingContext<? extends IActionEventProcessor> context);
}
