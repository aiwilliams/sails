package org.opensails.sails.form;

import java.io.IOException;
import java.util.Collection;

import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.oem.PrimitiveAdapter;
import org.opensails.sails.model.IPropertyAccessor;
import org.opensails.sails.model.IPropertyFactory;
import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.model.ModelContext;

/**
 * An html form post processor.
 * <p>
 * This encapsulates the behavior of:
 * <ul>
 * <li>transferring values into the model</li>
 * <li>validating the models using an ValidationContext</li>
 * </ul>
 * 
 * @author aiwilliams
 */
public class HtmlForm {
	protected FormFields formFields;
	protected ValidationContext validationContext;
	protected ModelContext modelContext;
	protected IPropertyFactory propertyFactory;
	protected ContainerAdapterResolver adapterResolver;

	/**
	 * @param validationContext used to validate the models after their values
	 *        have been transferred from the form fields into the models
	 * @param valueModel used to read and write the model during both render and
	 *        form post processing
	 * 
	 */
	public HtmlForm(ValidationContext validationContext, ModelContext modelContext, IPropertyFactory propertyFactory, ContainerAdapterResolver adapterResolver) {
		this.modelContext = modelContext;
		this.validationContext = validationContext;
		this.propertyFactory = propertyFactory;
		this.adapterResolver = adapterResolver;
	}

	/**
	 * @return all error messages
	 * @throws IOException
	 */
	public String getErrorMessages() {
		StringBuilder messages = new StringBuilder();
		Collection<IValidationFailure> failures = validationContext.getFailures();
		for (IValidationFailure failure : failures) {
			messages.append(failure.getMessage());
			messages.append("\n");
		}
		return messages.toString();
	}

	/**
	 * @return true if the last updateModels() was completed without errors
	 */
	public boolean isValid() {
		return validationContext.getFailures().isEmpty();
	}

	/**
	 * @param formFields the fields for updating the models
	 */
	public boolean updateModels(FormFields formFields) {
		this.formFields = formFields;
		for (String name : formFields.getNames()) {
			IPropertyPath path = propertyFactory.createPath(name);
			Object model = modelContext.getModel(path);
			if (model == null) continue;

			IPropertyAccessor accessor = propertyFactory.createAccessor(path);
			AdaptationTarget propertyTypeOnTarget = accessor.getPropertyType(model);
			IAdapter adapter = adapter(path, model, propertyTypeOnTarget);
			// TODO Adapters will take the adaptation target to support generics
			accessor.set(model, adapter.forModel(propertyTypeOnTarget.getTargetClass(), adapter.getFieldType().getValue(formFields, name)));
		}
		return isValid();
	}

	@SuppressWarnings("unchecked")
	public Object value(String propertyPath) {
		IPropertyPath path = propertyFactory.createPath(propertyPath);
		Object model = modelContext.getModel(path);
		if (model == null) return null;

		IPropertyAccessor accessor = propertyFactory.createAccessor(path);
		AdaptationTarget propertyTypeOnTarget = accessor.getPropertyType(model);
		IAdapter adapter = adapter(path, model, propertyTypeOnTarget);
		// TODO Adapters will take the adaptation target to support generics
		return adapter.forWeb(propertyTypeOnTarget.getTargetClass(), accessor.get(model));
	}

	protected IAdapter adapter(IPropertyPath path, Object model, AdaptationTarget adaptationTarget) {
		if (adaptationTarget.getTargetClass() == void.class) return new PrimitiveAdapter.StringAdapter();
		else return adapterResolver.resolve(adaptationTarget.getTargetClass());
	}
}
