package org.opensails.sails.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates to Sails that a particular behavior exists for an action or all
 * actions on a controller.
 * 
 * @author aiwilliams
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Behavior {
	Class<? extends IBehaviorHandler> value();
}
