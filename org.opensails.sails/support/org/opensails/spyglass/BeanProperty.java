package org.opensails.spyglass;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import org.apache.commons.lang.ArrayUtils;

public class BeanProperty<T> extends SpyProperty<T> {

	protected final PropertyDescriptor descriptor;

	protected BeanProperty(SpyClass<T> spyClass, PropertyDescriptor descriptor) {
		super(spyClass, descriptor.getName());
		this.descriptor = descriptor;
	}

	@Override
	public <X extends T> Object get(X target) {
		try {
			return descriptor.getReadMethod().invoke(target, ArrayUtils.EMPTY_OBJECT_ARRAY);
		} catch (IllegalArgumentException e) {
			throw new Crack("A bean getter that takes arguments?!?!", e);
		} catch (IllegalAccessException e) {
			throw new Crack("A bean getter that cannot be accessed?!?!", e);
		} catch (InvocationTargetException e) {
			throw new Crack("Exception occurred reading a bean property", e);
		}
	}

	@Override
	public Type getGenericType() {
		return descriptor.getReadMethod().getGenericReturnType();
	}

	@Override
	public Class<?> getType() {
		return descriptor.getPropertyType();
	}

	@Override
	public <X extends T> void set(X target, Object value) {
		try {
			descriptor.getWriteMethod().invoke(target, new Object[] { value });
		} catch (IllegalArgumentException e) {
			throw new Crack("Wrong arguments writing a bean property", e);
		} catch (IllegalAccessException e) {
			throw new Crack("A bean setter that cannot be accessed?!?!", e);
		} catch (InvocationTargetException e) {
			throw new Crack("Exception occurred writing a bean property", e);
		}
	}

}
