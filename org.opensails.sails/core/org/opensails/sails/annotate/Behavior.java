package org.opensails.sails.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates to Sails that a particular behavior exists for an action or all
 * actions on a controller.
 * 
 * @author aiwilliams
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Behavior {
	Class<? extends IBehaviorHandler> behavior();
}
