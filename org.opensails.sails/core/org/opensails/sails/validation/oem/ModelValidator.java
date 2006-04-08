package org.opensails.sails.validation.oem;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.SailsException;
import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidator;
import org.opensails.sails.validation.ValidatorClass;
import org.opensails.spyglass.SpyClass;

public class ModelValidator {
	protected Class modelClass;
	protected List<IPropertyValidator> fieldValidators;

	public ModelValidator(Class modelClass) {
		this.modelClass = modelClass;
		this.fieldValidators = fieldValidations(modelClass);
	}

	public IInvalidProperty[] invalidProperties(Object model) {
		List<IInvalidProperty> invalidProperties = new ArrayList<IInvalidProperty>();
		for (IPropertyValidator propertyValidator : fieldValidators) {
			try {
				IInvalidProperty invalid = propertyValidator.validate(model);
				if (invalid != null) invalidProperties.add(invalid);
			} catch (Exception e) {
				throw new SailsException("Failure validating model", e);
			}
		}
		return invalidProperties.toArray(new IInvalidProperty[invalidProperties.size()]);
	}

	@SuppressWarnings("unchecked")
	private List<IPropertyValidator> fieldValidations(Class annotatedClass) {
		List<IPropertyValidator> fieldValidators = new ArrayList<IPropertyValidator>();
		for (Field field : annotatedClass.getDeclaredFields()) {
			for (Annotation annotation : field.getAnnotations()) {
				Class<? extends Annotation> type = annotation.annotationType();
				if (type.isAnnotationPresent(ValidatorClass.class)) {
					SpyClass<? extends IValidator> validatorClass = new SpyClass<IValidator>((Class<IValidator>) type.getAnnotation(ValidatorClass.class).value());
					IValidator<Annotation> validatorInstance = validatorClass.newInstance();
					validatorInstance.init(annotation);
					fieldValidators.add(new FieldPropertyValidator(annotatedClass, field, validatorInstance));
				}
			}
		}
		return fieldValidators;
	}
}