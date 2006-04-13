package org.opensails.sails.component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * When a field is annotated thus, it won't get quoted in javascript land. At
 * least not by ComponentScript.
 */
public @interface LiteralJs {}
