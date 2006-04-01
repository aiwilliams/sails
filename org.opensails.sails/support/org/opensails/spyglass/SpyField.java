package org.opensails.spyglass;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Adds useful behavior to a Field.
 * <p>
 * The accessibility of the field is not determined by this, but by the
 * SpyPolicy of the SpyClass that created this. It checks the policy at each
 * invocation to allow for changes in the policy.
 * 
 * @author aiwilliams
 */
public class SpyField<T> extends SpyProperty<T> {
	protected final Field field;

	protected SpyField(SpyClass<T> spyClass, Field field) {
		super(spyClass, field.getName());
		this.field = field;
	}

	@Override
	public <X extends T> Object get(X target) {
		if (spyClass.policy.getFieldAccessPolicy().canAccess(field)) field.setAccessible(true);
		try {
			return field.get(target);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Type getGenericType() {
		return field.getGenericType();
	}

	@Override
	public Class<?> getType() {
		return field.getType();
	}

	@Override
	public <X extends T> void set(X target, Object value) {
		if (spyClass.policy.getFieldAccessPolicy().canAccess(field)) field.setAccessible(true);
		try {
			field.set(target, value);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
