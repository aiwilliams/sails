package org.opensails.spyglass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.opensails.sails.SailsException;

public class SpyClass<T> {
	protected final Class<T> type;

	public SpyClass(Class<T> type) {
		this.type = type;
	}

	public String getPackageName() {
		return ClassUtils.getPackageName(type);
	}

	public T newInstance() {
		return newInstance(ArrayUtils.EMPTY_OBJECT_ARRAY);
	}

	public T newInstance(Object[] args) {
		try {
			return findConstructor(type, SpyGlass.argTypes(args)).newInstance(args);
		} catch (InstantiationException e) {
			throw new Crack(String.format("Could not instantiate a %s. No constructor matching argument types?", type), e);
		} catch (IllegalAccessException e) {
			throw new Crack("Could not instantiate. Know anything about access?", e);
		} catch (IllegalArgumentException e) {
			throw new Crack("Could not instantiate. The arguments were illegal.", e);
		} catch (SecurityException e) {
			throw new Crack("Could not instantiate. Know anything about security?", e);
		} catch (InvocationTargetException e) {
			throw new Crack("Could not instantiate. The constructor threw and exception.", e);
		}
	}

	public SpyObject<T> spyInstance(Object[] args) {
		return new SpyObject<T>(newInstance(args));
	}

	private Constructor<T> findConstructor(Class<T> clazz, Class[] argTypes) {
		Constructor[] constructors = clazz.getConstructors();
		for (Constructor<T> constructor : constructors) {
			if (SpyGlass.argTypesExtendThese(argTypes, constructor.getParameterTypes())) return constructor;
		}
		throw new SailsException("Could not find a constructor accepting " + argTypes);
	}

}
