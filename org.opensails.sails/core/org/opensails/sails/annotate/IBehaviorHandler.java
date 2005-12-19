package org.opensails.sails.annotate;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import org.opensails.sails.event.IEventProcessingContext;

/**
 * Defines the actual code behind a Behavior declaration. These are created an
 * called during the lifecycle of an action invocation.
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IBehaviorHandler {
	void afterAction(Annotation behavior, ElementType type, IEventProcessingContext context);

	void beforeAction(Annotation behavior, ElementType type, IEventProcessingContext context);
}
