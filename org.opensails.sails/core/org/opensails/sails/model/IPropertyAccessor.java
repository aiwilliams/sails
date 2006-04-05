package org.opensails.sails.model;

import org.opensails.sails.adapter.AdaptationTarget;

/**
 * Wraps an {@link IPropertyPath} to support reading and writing the property
 * represented by that path.
 * <p>
 * IPropertyAccessors are coupled to a particular type of IPropertyPath. This
 * allows you to create custom types of paths and accessors that understand
 * them.
 */
public interface IPropertyAccessor {
	Object get(Object model) throws PropertyAccessException;

	/**
	 * @return the AdaptationTarget of this property on the model
	 */
	AdaptationTarget getAdaptationTarget(Object model);

	void set(Object model, Object value) throws PropertyAccessException;
}
