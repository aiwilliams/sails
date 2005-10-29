package org.opensails.sails.validation;

import org.opensails.sails.validation.constraints.Length;

public class ShamValidatedModel {
    @Length(min=5)
    protected String propertyNoSetter;
}
