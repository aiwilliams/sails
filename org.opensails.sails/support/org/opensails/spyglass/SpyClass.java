package org.opensails.spyglass;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.opensails.sails.SailsException;
import org.opensails.spyglass.policy.SpyPolicy;

/**
 * Provides very useful behavior to Class.
 * <p>
 * The SpyPolicy given to this is used when instantiating new instances. The
 * policy is consulted when invoking constructors and is given to
 * spyInstance()'s.
 * 
 * @author aiwilliams
 * 
 * @param <T>
 */
public class SpyClass<T> {
	protected final Class<T> type;
	protected final SpyPolicy policy;

	private HashMap<String, SpyProperty<T>> propertiesMap = new HashMap<String, SpyProperty<T>>();

	public SpyClass(Class<T> type) {
		this(type, SpyPolicy.WIDEOPEN);
	}

	public SpyClass(Class<T> type, SpyPolicy policy) {
		this.type = type;
		this.policy = policy;
	}

	/**
	 * Find the property using PropertyDescriptors. Only called when not in
	 * cache.
	 * 
	 * @param name
	 * @return the property or null if not found
	 */
	private SpyProperty<T> findBeanProperty(String name) {
		for (PropertyDescriptor descriptor : getPropertyDescriptors()) {
			SpyProperty<T> property = new BeanProperty<T>(this, descriptor);
			propertiesMap.put(name, property);
			if (descriptor.getName().equals(name)) return property;
		}
		return null;
	}

	private Constructor<T> findConstructor(Class<T> clazz, Class[] argTypes) {
		Constructor[] constructors = clazz.getConstructors();
		for (Constructor<T> constructor : constructors) {
			if (SpyGlass.argTypesExtendThese(argTypes, constructor.getParameterTypes())) return constructor;
		}
		throw new SailsException("Could not find a constructor accepting " + argTypes);
	}

	/**
	 * Find the property as a field. Only called when not in cache. Caches
	 * accessible and inaccessible field properties.
	 * 
	 * @param name
	 * @return the property or null if not found
	 */
	private SpyProperty<T> findFieldProperty(String name) {
		for (Field field : getFields()) {
			String fieldName = field.getName();
			// not the instance of containing class when anonymous innerclass
			if (fieldName.indexOf('$') == -1) {
				SpyProperty<T> property = null;
				if (policy.canAccess(field)) property = new SpyField<T>(this, field);
				else property = new InaccessibleFieldProperty<T>(this, field);
				propertiesMap.put(name, property);
				if (fieldName.equals(name)) return property;
			}
		}
		return null;
	}

	public SpyProperty<T> findProperty(String name) {
		SpyProperty<T> property = propertiesMap.get(name);
		if (property == null) {
			property = findBeanProperty(name);
			if (property == null) property = findFieldProperty(name);
			if (property == null) property = new UnresolvableProperty<T>(this, name);
			propertiesMap.put(name, property);
		}
		return property;
	}

	/**
	 * @param name
	 * @return the declared field in class heirarchy, null if not present
	 */
	public Field getField(String name) {
		for (Field field : getFields())
			if (field.getName().equals(name)) return field;
		return null;
	}

	/**
	 * @return all declared fields in class heirarchy
	 */
	public Collection<Field> getFields() {
		Collection<Field> fields = new ArrayList<Field>();
		Class nextClass = type;
		while (nextClass != null) {
			for (Field field : nextClass.getDeclaredFields())
				fields.add(field);
			nextClass = nextClass.getSuperclass();
		}
		return fields;
	}

	protected Collection<Class> getInterfaces(Class testClass) {
		List<Class> list = new ArrayList<Class>();
		Class[] testClassInterfaces = testClass.getInterfaces();
		for (Class interfaze : testClassInterfaces)
			list.addAll(getInterfaces(interfaze));
		list.add(testClass);
		return list;
	}

	/**
	 * @return all declared methods in class heirarchy, including Object
	 */
	public Collection<Method> getMethods() {
		Collection<Method> methods = new ArrayList<Method>();
		Class nextClass = type;
		while (nextClass != null) {
			for (Method method : nextClass.getDeclaredMethods())
				methods.add(method);
			nextClass = nextClass.getSuperclass();
		}
		return methods;
	}

	/**
	 * @param name
	 * @param argTypes
	 * @return a method taking the argTypes or subclasses thereof, or null if
	 *         not found
	 */
	public Method getMethodTaking(String name, Class[] argTypes) {
		Class nextClass = type;
		while (nextClass != null) {
			for (Method method : nextClass.getDeclaredMethods())
				if (method.getName().equals(name) && SpyGlass.argTypesExtendThese(argTypes, method.getParameterTypes())) return method;
			nextClass = nextClass.getSuperclass();
		}
		return null;
	}

	public String getPackageName() {
		return ClassUtils.getPackageName(type);
	}

	public Collection<PropertyDescriptor> getPropertyDescriptors() {
		if (type.isInterface()) return getPropertyDescriptorsForInterfaces();
		else return getPropertyDescriptorsForConcreteClasses();
	}

	protected Collection<PropertyDescriptor> getPropertyDescriptorsForConcreteClasses() {
		return rawGetPropertyDescriptors(type);
	}

	protected Collection<PropertyDescriptor> getPropertyDescriptorsForInterfaces() {
		Collection<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
		Collection<Class> interfaceList = getInterfaces(type);
		for (Class interfaze : interfaceList)
			list.addAll(rawGetPropertyDescriptors(interfaze));
		return list;
	}

	public Class<?> getPropertyType(String property) {
		return findProperty(property).getType();
	}

	@SuppressWarnings("unchecked")
	public SpyClass<?> getSpyPropertyType(String property) {
		return new SpyClass(getPropertyType(property), policy);
	}

	public Class getType() {
		return type;
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

	protected Collection<PropertyDescriptor> rawGetPropertyDescriptors(Class testClass) {
		Collection<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(testClass);
			for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors())
				list.add(descriptor);
		} catch (IntrospectionException e) {}
		return list;
	}

	public SpyObject<T> spyInstance(Object[] args) {
		return new SpyObject<T>(newInstance(args), policy);
	}

}
