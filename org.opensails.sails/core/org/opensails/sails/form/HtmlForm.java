package org.opensails.sails.form;

import java.util.Map;
import java.util.Map.Entry;

import org.opensails.sails.RequestContainer;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.model.AccessorException;
import org.opensails.sails.model.IModelContext;
import org.opensails.sails.model.IPropertyAccessor;
import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.model.PropertyPathException;
import org.opensails.sails.model.oem.DotPropertyPath;
import org.opensails.sails.model.oem.PropertyAccessor;
import org.opensails.sails.util.WriteOnceHashMap;
import org.opensails.sails.validation.IInvalidProperty;
import org.opensails.sails.validation.IValidationEngine;
import org.opensails.sails.validation.IValidationResult;

/**
 * This encapsulates the behavior of:
 * <ul>
 * <li>transferring values into the model</li>
 * <li>validating the model using an IValidationEngine</li>
 * <li>keeping reference to all invalid fields</li>
 * </ul>
 * <p>
 * The IFormValueModel is the interface used by the FormMixin (basically an html
 * generator factory) to transfer values out of a model into the view. These
 * parts need to come together somehow, and they will soon. The other thing
 * missing is decorators around the generators used by the FormMixin. When a
 * field is invalid, an application may want to do something pretty.
 * 
 * @author aiwilliams
 */
public class HtmlForm {
	protected final IAdapterResolver adapterResolver;
	protected final RequestContainer container;
	protected final FormFields formFields;
	protected Map<String, IInvalidProperty> invalids;
	protected final IModelContext modelContext;
	protected final IValidationEngine validationEngine;

	public HtmlForm(RequestContainer container, IModelContext modelContext, FormFields formFields, IAdapterResolver adapterResolver, IValidationEngine validationEngine) {
		this.container = container;
		this.modelContext = modelContext;
		this.formFields = formFields;
		this.adapterResolver = adapterResolver;
		this.validationEngine = validationEngine;
	}

	/**
	 * @return the current message for the form, which will be null if the form
	 *         is valid
	 */
	public String getMessage() {
		if (isValid()) return null;

		StringBuilder message = new StringBuilder("Invalid fields:\n");
		for (Entry invalid : invalids.entrySet()) {
			message.append(invalid.getKey());
			message.append(" : ");
			message.append(invalid.getValue());
		}
		return message.toString();
	}

	/**
	 * @return true if there were no problems adapting values and writing them
	 *         to the model
	 */
	public boolean isValid() {
		if (invalids == null) {
			invalids = new WriteOnceHashMap<String, IInvalidProperty>();
			for (String fieldName : formFields.getNames()) {
				IPropertyPath path = null;
				Object model = null;
				if (!isMetaField(fieldName)) try {
					path = fieldPropertyPath(fieldName);
					model = modelContext.getModel(path);
					IPropertyAccessor accessor = accessor(path);
					Class propertyTypeOnTarget = accessor.getPropertyType(model);
					IAdapter adapter = adapter(path, propertyTypeOnTarget);
					accessor.set(model, adapter.forModel(propertyTypeOnTarget, formFields.valueAs(fieldName, adapter.getFieldType())));

					// now that everything is transferred, validate
					IValidationResult validationResult = validationEngine.validate(model);
					for (IInvalidProperty invalid : validationResult.getInvalidProperties())
						invalids.put(invalid.getProperty(), invalid);
				} catch (AdaptationException e) {
					invalids.put(path.getProperty(), new UnadaptableProperty(fieldName, path, model, e));
				} catch (AccessorException e) {
					invalids.put(path.getProperty(), new InaccessibleProperty(fieldName, path, model, e));
				} catch (PropertyPathException e) {
					invalids.put(fieldName, new InvalidProperty(fieldName, path, model, e));
				}
			}
		}
		return invalids.isEmpty();
	}

	public void resetValidation() {
		invalids = null;
	}

	protected IPropertyAccessor accessor(IPropertyPath path) {
		return new PropertyAccessor(path, true);
	}

	protected IAdapter adapter(IPropertyPath path, Class propertyTypeOnTarget) {
		return adapterResolver.resolve(propertyTypeOnTarget, container);
	}

	protected IPropertyPath fieldPropertyPath(String fieldName) {
		if (fieldName.indexOf('.') == -1) return new DotPropertyPath(fieldName, "undefined");
		else return new DotPropertyPath(fieldName);
	}

	protected boolean isMetaField(String fieldName) {
		return fieldName != null && fieldName.startsWith(FormMeta.META_PREFIX);
	}
}
