package org.opensails.spyglass;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
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

	public SpyProperty<T> findProperty(String name) {
		SpyProperty<T> property = propertiesMap.get(name);
		if (property == null) {
			property = new SpyProperty<T>(this, name);
			propertiesMap.put(name, property);
		}
		return property;
	}

	/**
	 * @param name
	 * @return the declared field in class heirarchy having name, null if not
	 *         present
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

	public Collection<Field> getFieldsAnnotated(Class<? extends Annotation> annotationClass) {
		return filterByAnnotation(getFields(), annotationClass, false);
	}

	public Collection<Field> getFieldsNotAnnotated(Class<? extends Annotation> annotationClass) {
		return filterByAnnotation(getFields(), annotationClass, true);
	}

	/**
	 * @param name
	 * @return the first declared method in class hierarchy having name, null if
	 *         not present
	 */
	public Method getMethod(String name) {
		for (Method method : getMethods())
			if (method.getName().equals(name)) return method;
		return null;
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
	 * @return the first declared method in class hierarchy having name, null if
	 *         not present
	 */
	public Collection<Method> getMethods(String name) {
		List<Method> methods = new ArrayList<Method>();
		for (Method method : getMethods())
			if (method.getName().equals(name)) methods.add(method);
		return methods;
	}

	public Collection<Method> getMethodsAnnotated(Class<? extends Annotation> annotationClass) {
		return filterByAnnotation(getMethods(), annotationClass, false);
	}

	public Collection<Method> getMethodsNotAnnotated(Class<? extends Annotation> annotationClass) {
		return filterByAnnotation(getMethods(), annotationClass, true);
	}

	public Package getPackage() {
		return type.getPackage();
	}

	public String getPackageName() {
		return getPackage().getName();
	}

	public SpyPolicy getPolicy() {
		return policy;
	}

	public Collection<PropertyDescriptor> getPropertyDescriptors() {
		if (type.isInterface()) return getPropertyDescriptorsForInterfaces();
		else return getPropertyDescriptorsForConcreteClasses();
	}

	public Class<?> getPropertyType(String property) {
		return findProperty(property).getType();
	}

	public SpyMethod getSpyMethod(String name) {
		return new SpyMethod<T>(this, name);
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

	public SpyObject<T> spyInstance(Object[] args) {
		return new SpyObject<T>(newInstance(args), policy);
	}

	protected <E extends AnnotatedElement> Collection<E> filterByAnnotation(Collection<E> all, Class<? extends Annotation> annotationClass, boolean inverse) {
		Collection<E> filtered = new ArrayList<E>(all.size());
		for (E each : all)
			if (inverse ^ each.isAnnotationPresent(annotationClass))
				filtered.add(each);
		return filtered;
	}

	protected Collection<Class> getInterfaces(Class testClass) {
		List<Class> list = new ArrayList<Class>();
		Class[] testClassInterfaces = testClass.getInterfaces();
		for (Class interfaze : testClassInterfaces)
			list.addAll(getInterfaces(interfaze));
		list.add(testClass);
		return list;
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
	
	protected Collection<PropertyDescriptor> rawGetPropertyDescriptors(Class testClass) {
		Collection<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(testClass);
			for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors())
				list.add(descriptor);
		} catch (IntrospectionException e) {}
		return list;
	}
	
	private Constructor<T> findConstructor(Class<T> clazz, Class[] argTypes) {
		Constructor[] constructors = clazz.getConstructors();
		for (Constructor<T> constructor : constructors) {
			if (SpyGlass.argTypesExtendThese(argTypes, constructor.getParameterTypes())) return constructor;
		}
		throw new SailsException("Could not find a constructor accepting " + argTypes);
	}

}
