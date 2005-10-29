package org.opensails.sails.validation.oem;

import java.lang.reflect.Field;

import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidator;

public class InvalidFieldProperty implements IInvalidProperty {
    private final Class annotatedClass;
    private final Field annotatedField;
    private final IValidator validator;

    public InvalidFieldProperty(Class annotatedClass, Field annotatedField, IValidator validator) {
        this.annotatedClass = annotatedClass;
        this.annotatedField = annotatedField;
        this.validator = validator;
    }

    public String getProperty() {
        // TODO Auto-generated method stub
        return null;
    }

}
