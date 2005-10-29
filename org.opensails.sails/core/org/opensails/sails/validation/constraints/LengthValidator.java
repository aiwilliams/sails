package org.opensails.sails.validation.constraints;

import org.opensails.sails.validation.IValidator;
import org.opensails.sails.validation.InvalidPropertyException;

public class LengthValidator implements IValidator<Length> {
    protected int min;
    protected int max;

    public void init(Length constraint) {
        min = constraint.min();
        max = constraint.max();
    }

    public void validate(Object value) throws InvalidPropertyException {
        if (value != null) {
        if (!(value instanceof String))
            throw new InvalidPropertyException("must be a String");
        String string = (String) value;
        int length = string.length();
        if (length < min || length > max)
            throw new InvalidPropertyException("must contain " + min + " to " + max + " characters");
        }
    }
}