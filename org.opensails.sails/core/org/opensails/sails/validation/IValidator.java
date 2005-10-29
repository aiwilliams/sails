package org.opensails.sails.validation;

import java.lang.annotation.Annotation;

public interface IValidator<A extends Annotation> {
    void init(A constraint);
    void validate(Object value) throws InvalidPropertyException;
}
