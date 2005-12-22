package org.opensails.sails.form;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.model.IPropertyAccessor;
import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.model.oem.DotPropertyPath;
import org.opensails.sails.model.oem.PropertyAccessor;

public class FormValueModel implements IFormValueModel {
	protected final ContainerAdapterResolver adapterResolver;
	protected final Map<String, Object> exposed = new HashMap<String, Object>();

	public FormValueModel(ContainerAdapterResolver adapterResolver) {
		this.adapterResolver = adapterResolver;
	}

	public void expose(String name, Object model) {
		exposed.put(name, model);
	}

	@SuppressWarnings("unchecked")
	public Object value(String fieldName) {
		IPropertyPath path = propertyPath(fieldName);
		Object model = model(path);
		if (model == null) return null;
		IPropertyAccessor accessor = new PropertyAccessor(path, true);
		Class propertyTypeOnTarget = accessor.getPropertyType(model);
		IAdapter adapter = adapterResolver.resolve(propertyTypeOnTarget);
		return adapter.forWeb(propertyTypeOnTarget, accessor.get(model));
	}

	protected Object model(IPropertyPath path) {
		String name = path.getTargetIdentifier();
		Object model = exposed.get(name);
		if (model == null && exposed.values().size() == 1) model = exposed.values().iterator().next();
		return model;
	}

	protected IPropertyPath propertyPath(String fieldName) {
		if (fieldName.indexOf('.') == -1) return new DotPropertyPath(fieldName, "undefined");
		else return new DotPropertyPath(fieldName);
	}
}
