package org.opensails.sails.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opensails.sails.validation.ValidatorClass;

@Documented
@ValidatorClass(NotNullValidator.class)
@Target( { ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
	String DEFAULT_MESSAGE = "must have a value";

	String message() default DEFAULT_MESSAGE;
}
