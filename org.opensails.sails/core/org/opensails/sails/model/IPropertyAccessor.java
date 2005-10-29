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
    /**
     * Declares what the values for/from the web should be, either StringArray
     * or String. This allows for the accessor, the thing that modifies the
     * model, to control how the model state is represented in an html ui.
     */
    enum FieldType {
        STRING,
        STRING_ARRAY,
    }

    Object get(Object model) throws AccessorException;

    void set(Object model, Object value) throws AccessorException;

    Class getPropertyType(Object model) throws AccessorException;

    FieldType getFieldType(Object model) throws AccessorException;
}
