package org.opensails.sails.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opensails.sails.validation.ValidatorClass;

@Documented
@ValidatorClass(LengthValidator.class)
@Target( { ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
	String DEFAULT_MESSAGE = "has an invalid length";

	int max() default Integer.MAX_VALUE;

	String message() default DEFAULT_MESSAGE;

	int min();
}