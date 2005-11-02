package org.opensails.sails.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Used on IControllerImpl implementations to declare their layouts. It can be used
 * on the class or on individual methods. The method declaration supercedes
 * those defined on the class.
 */
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface Layout {
	String value();
}
