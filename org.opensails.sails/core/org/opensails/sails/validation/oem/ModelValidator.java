package org.opensails.sails.validation.oem;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidator;
import org.opensails.sails.validation.ValidatorClass;

public class ModelValidator {
    private final Class modelClass;
    private final List<IPropertyValidator> fieldValidators;

    public ModelValidator(Class modelClass) {
        this.modelClass = modelClass;
        this.fieldValidators = fieldValidations(modelClass);
    }

    private List<IPropertyValidator> fieldValidations(Class annotatedClass) {
        List<IPropertyValidator> fieldValidators = new ArrayList();
        for (Field field : annotatedClass.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                Class<? extends Annotation> type = annotation.annotationType();
                if (type.isAnnotationPresent(ValidatorClass.class)) {
                    Class<? extends IValidator> validatorClass = type.getAnnotation(ValidatorClass.class).value();
                    try {
                        IValidator validatorInstance = validatorClass.newInstance();
                        validatorInstance.init(annotation);
                        fieldValidators.add(new FieldPropertyValidator(annotatedClass, field, validatorInstance));
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fieldValidators;
    }

    public IInvalidProperty[] invalidProperties(Object model) {
        List<IInvalidProperty> invalidProperties = new ArrayList<IInvalidProperty>();
        for (IPropertyValidator propertyValidator : fieldValidators) {
            IInvalidProperty invalid = propertyValidator.validate(model);
            if (invalid != null) invalidProperties.add(invalid);
        }
        return invalidProperties.toArray(new IInvalidProperty[invalidProperties.size()]);
    }
}