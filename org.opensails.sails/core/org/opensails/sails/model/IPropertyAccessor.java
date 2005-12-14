package org.opensails.sails.model;

/**
 * Can read and write a property on something, to any depth, using an
 * IPropertyPath.
 * 
 * These support reading properties from a {@link java.beans.PropertyDescriptor}
 * and {@link java.lang.reflect.Field}. Of course, you can make them do more,
 * too.
 */
public interface IPropertyAccessor {
	Object get(Object model) throws AccessorException;

	Class getPropertyType(Object model) throws AccessorException;

	void set(Object model, Object value) throws AccessorException;
}
