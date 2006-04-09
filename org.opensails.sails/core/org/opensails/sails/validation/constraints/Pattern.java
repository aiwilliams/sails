package org.opensails.sails.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.opensails.sails.validation.ValidatorClass;

@Documented
@ValidatorClass(PatternValidator.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface Pattern {
	String DEFAULT_MESSAGE = "must match pattern";

	int flags() default 0;

	String message() default DEFAULT_MESSAGE;

	String regex();
}