package org.opensails.sails.template;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opensails.sails.annotate.Behavior;

/**
 * Used on IControllerImpl implementations to declare no layout.
 * <p>
 * It can be used on the class or on individual methods. The method declaration
 * supercedes those defined on the class.
 */
@Documented
@Behavior(LayoutHandler.class)
@Target( { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLayout {}
