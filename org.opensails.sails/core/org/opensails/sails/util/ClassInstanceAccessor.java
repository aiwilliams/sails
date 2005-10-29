package org.opensails.sails.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.model.AccessorException;
import org.opensails.sails.model.oem.UnknownPropertyException;

public class ClassInstanceAccessor {
	protected final boolean accessPrivates;
	protected final Class<?> clazz;
	protected Map<String, Property> propertiesMap;
	protected Set<Property> propertiesSet;

	public ClassInstanceAccessor(Class<?> clazz) {
		this(clazz, false);
	}

	public ClassInstanceAccessor(Class<?> clazz, boolean accessPrivates) {
		this.clazz = clazz;
		this.accessPrivates = accessPrivates;
		initializeProperties();
	}

	public Object getProperty(Object object, String propertyName) {
		validateClass(object);
		return getProperty(propertyName).get(object);
	}

	public List<String> getPropertyNames() {
		List<String> list = new ArrayList<String>();
		for (Property property : getProperties())
			list.add(property.getName());
		return list;
	}

	public Class getPropertyType(String propertyName) {
		return getProperty(propertyName).getType();
	}

	public boolean isProperty(String propertyName) {
		return propertiesMap.containsKey(propertyName);
	}

	public boolean isPropertyReadable(String propertyName) {
		if (isProperty(propertyName)) return getProperty(propertyName).isReadable();
		return false;
	}

	public void setProperty(Object object, String propertyName, Object newValue) {
		validateClass(object);
		getProperty(propertyName).set(object, newValue);
	}

	protected Collection<Property> getFieldProperties() {
		List<Property> props = new ArrayList<Property>();
		for (Field field : getFields(accessPrivates)) {
			String name = field.getName();
			// not the instance of containing class when anonymous innerclass
			if (name.indexOf('$') == -1) {
				Property property = new Property(name);
				property.setField(field);
				props.add(property);
			}
		}
		return props;
	}

	protected Collection<Field> getFields(boolean accessPrivates) {
		List<Field> fields = new ArrayList<Field>();
		Class nextClass = clazz;
		while (nextClass != null) {
			for (Field field : nextClass.getDeclaredFields()) {
				if (accessPrivates || !Modifier.isPrivate(field.getModifiers())) {
					if (!field.isAccessible()) field.setAccessible(true);
					fields.add(field);
				}
			}
			nextClass = nextClass.getSuperclass();
		}
		return fields;
	}

	protected List<Class> getInterfaces(Class testClass) {
		List<Class> list = new ArrayList<Class>();
		Class[] testClassInterfaces = testClass.getInterfaces();
		for (Class interfaze : testClassInterfaces)
			list.addAll(getInterfaces(interfaze));
		list.add(testClass);
		return list;
	}

	protected Set<Property> getProperties() {
		return propertiesSet;
	}

	protected Property getProperty(String name) {
		Property property = propertiesMap.get(name);
		if (property == null) throw new UnknownPropertyException(clazz, name);
		return property;
	}

	protected List<PropertyDescriptor> getPropertyDescriptors(Class testClass) {
		if (testClass.isInterface()) return getPropertyDescriptorsForInterfaces(testClass);
		else return getPropertyDescriptorsForConcreteClasses(testClass);
	}

	protected List<PropertyDescriptor> getPropertyDescriptorsForConcreteClasses(Class testClass) {
		return rawGetPropertyDescriptors(testClass);
	}

	protected List<PropertyDescriptor> getPropertyDescriptorsForInterfaces(Class testClass) {
		List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
		List<Class> interfaceList = getInterfaces(testClass);
		for (Class interfaze : interfaceList)
			list.addAll(rawGetPropertyDescriptors(interfaze));
		return list;
	}

	protected void initializeProperties() {
		propertiesMap = new HashMap<String, Property>();
		for (Property property : getFieldProperties())
			propertiesMap.put(property.getName(), property);
		for (PropertyDescriptor descriptor : getPropertyDescriptors(clazz)) {
			Property property = propertiesMap.get(descriptor.getName());
			if (property == null) {
				property = new Property(descriptor.getName());
				propertiesMap.put(descriptor.getName(), property);
			}
			property.setDescriptor(descriptor);
		}
		propertiesSet = new HashSet<Property>();
		propertiesSet.addAll(propertiesMap.values());
	}

	protected List<PropertyDescriptor> rawGetPropertyDescriptors(Class testClass) {
		List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(testClass);
			for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors())
				list.add(descriptor);
		} catch (IntrospectionException e) {}
		return list;
	}

	protected void validateClass(Object object) {
		if (!clazz.isAssignableFrom(object.getClass())) throw new IllegalArgumentException("Argument must be a subclass of " + clazz);
	}

	class Property {
		private PropertyDescriptor descriptor;
		private Field field;
		private final String name;

		Property(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object obj) {
			return ((Property) obj).getName().equals(getName());
		}

		@Override
		public int hashCode() {
			return getName().hashCode();
		}

		public void set(Object object, Object newValue) {
			if (descriptor != null) {
				Method writeMethod = descriptor.getWriteMethod();
				if (writeMethod != null) try {
					writeMethod.invoke(object, new Object[] { newValue });
					return;
				} catch (InvocationTargetException e) {
					throw new AccessorException(name, object, "An exception occurred in the write method", e);
				} catch (Exception e) {
					throw new AccessorException(name, object, "Could not use the write method", e);
				}
			}

			if (field != null) try {
				field.set(object, newValue);
				return;
			} catch (Exception e) {
				throw new AccessorException(name, object, "An exception occurred writing the field", e);
			}

			throw new AccessorException(name, object, "There is no way to write the property");
		}

		Object get(Object object) {
			if (descriptor != null) {
				Method readMethod = descriptor.getReadMethod();
				if (readMethod != null) try {
					return readMethod.invoke(object, ArrayUtils.EMPTY_OBJECT_ARRAY);
				} catch (InvocationTargetException e) {
					throw new AccessorException(name, object, "An exception occurred in the read method", e);
				} catch (Exception e) {
					throw new AccessorException(name, object, "Could not use the read method", e);
				}
			}

			if (field != null) try {
				return field.get(object);
			} catch (Exception e) {
				throw new AccessorException(name, object, "An exception occurred reading the field", e);
			}

			throw new AccessorException(name, object, "There is no way to read the property");
		}

		String getName() {
			return name;
		}

		Class getType() {
			return field != null ? field.getType() : descriptor.getPropertyType();
		}

		boolean isReadable() {
			return field != null || descriptor.getReadMethod() != null;
		}

		void setDescriptor(PropertyDescriptor propertyDescriptor) {
			descriptor = propertyDescriptor;
		}

		void setField(Field field) {
			this.field = field;
		}
	}
}