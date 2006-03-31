package org.opensails.spyglass;

import java.lang.reflect.Field;

import org.opensails.sails.util.BleedingEdgeException;

/**
 * When a SpyClass has a field, but not a bean property, and that field is
 * inaccessible (due to the policy or other), this is what you get.
 * 
 * @author aiwilliams
 */
public class InaccessibleFieldProperty<T> extends SpyField<T> {

	protected InaccessibleFieldProperty(SpyClass<T> spyClass, Field field) {
		super(spyClass, field);
	}

	@Override
	public <X extends T> Object get(X target) {
		throw new BleedingEdgeException("what should we do if accessing inaccessible field? the policy made it so.");
	}

	@Override
	public <X extends T> void set(X target, Object value) {
		throw new BleedingEdgeException("what should we do if accessing inaccessible field? the policy made it so.");
	}

}
