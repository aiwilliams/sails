package org.opensails.sails.model.oem;

import org.opensails.sails.model.AccessorException;
import org.opensails.sails.model.IPropertyAccessor;
import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.util.ClassInstanceAccessor;

/**
 * Accesses properties on the target using
 * {@link org.opensails.sails.util.ClassInstanceAccessor}s.
 */
public class PropertyAccessor implements IPropertyAccessor {
	protected boolean accessPrivates;
	protected IPropertyPath path;

	/**
	 * @param path
	 * @param accessPrivates
	 */
	public PropertyAccessor(IPropertyPath path, boolean accessPrivates) {
		this.path = path;
		this.accessPrivates = accessPrivates;
	}

	public Object get(Object model) throws AccessorException {
		return getPropertyAccessor(model, model.getClass()).getProperty(model, path.getProperty());
	}

	public Class getPropertyType(Object model) throws AccessorException {
		return getPropertyAccessor(model, model.getClass()).getPropertyType(path.getProperty());
	}

	public void set(Object model, Object value) throws AccessorException {
		getPropertyAccessor(model, model.getClass()).setProperty(model, path.getProperty(), value);
	}

	protected ClassInstanceAccessor getPropertyAccessor(Object model, Class<? extends Object> clazz) {
		return new ClassInstanceAccessor(clazz, accessPrivates);
	}
}
