package org.opensails.sails.template;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opensails.sails.annotate.Behavior;

/**
 * Used on IControllerImpl implementations to declare their layouts.
 * <p>
 * It can be used on the class or on individual methods. The method declaration
 * supercedes those defined on the class. If, at the point at which this
 * behavior is invoked before the action is executed, there is already a result,
 * this will do nothing. Please keep this in mind if you have BeforeFilters or
 * other behaviors that want to control the ActionResult.
 */
@Documented
@Behavior(LayoutHandler.class)
@Target( { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Layout {
	String value();
}
