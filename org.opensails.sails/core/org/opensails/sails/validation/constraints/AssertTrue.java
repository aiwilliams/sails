package org.opensails.sails.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opensails.sails.validation.ValidatorClass;

@Documented
@ValidatorClass(TruthValidator.class)
@Target( { ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertTrue {
	String message() default "expected to be true";
}
