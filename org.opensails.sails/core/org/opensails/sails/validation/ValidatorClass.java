package org.opensails.sails.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorClass {
    Class<? extends IValidator> value();
}
