package org.opensails.sails.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PropertyFieldAccessor {
    protected Class clazz;

    public PropertyFieldAccessor(Class clazz) {
        this.clazz = clazz;
    }

    protected List<Class> getInterfaces(Class testClass) {
        List<Class> list = new ArrayList<Class>();
        Class[] testClassInterfaces = testClass.getInterfaces();
        for (Class interfaze : testClassInterfaces)
            list.addAll(getInterfaces(interfaze));
        list.add(testClass);
        return list;
    }

    protected PropertyDescriptor getPropertyDescriptor(Class testClass, String propertyName) {
        for (PropertyDescriptor descriptor : getPropertyDescriptors(testClass))
            if (descriptor.getName().equalsIgnoreCase(propertyName)) return descriptor;
        return null;
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

    protected Object invokeGet(Object object, Method method) {
        try {
            return method.invoke(object, new Object[0]);
        } catch (Exception e) {
            // TODO: Need something more intelligent
            throw new RuntimeException(e);
        }
    }

    protected void invokeSet(Method method, Object object, Object newValue) {
        try {
            method.invoke(object, new Object[] { newValue });
        } catch (Exception e) {
            // TODO: Need something more intelligent
            throw new RuntimeException(e);
        }
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

    @SuppressWarnings("unchecked")
    protected void validateClass(Object object) {
        if (!clazz.isAssignableFrom(object.getClass())) throw new IllegalArgumentException("Argument must be a subclass of " + clazz);
    }

    public Object getProperty(Object object, String propertyName) {
        validateClass(object);

        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(object.getClass(), propertyName);
        if (propertyDescriptor == null) return null;

        Method method = propertyDescriptor.getReadMethod();
        if (method == null) return null;

        return invokeGet(object, method);
    }

    public List<String> getPropertyNames() {
        List<String> list = new ArrayList<String>();
        for (PropertyDescriptor descriptor : getPropertyDescriptors(clazz))
            list.add(descriptor.getName());
        return list;
    }

    public Class getPropertyType(String propertyName) {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clazz, propertyName);
        if (propertyDescriptor == null) return null;

        return propertyDescriptor.getPropertyType();
    }

    public boolean isProperty(String propertyName) {
        return getPropertyDescriptor(clazz, propertyName) != null;
    }

    public boolean isPropertyReadable(String propertyName) {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clazz, propertyName);
        if (propertyDescriptor == null) return false;

        if (propertyDescriptor.getReadMethod() == null) return false;

        return true;
    }

    public void setProperty(Object object, String propertyName, Object newValue) {
        validateClass(object);

        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(object.getClass(), propertyName);
        if (propertyDescriptor == null) return;

        Method method = propertyDescriptor.getWriteMethod();
        if (method == null) return;

        invokeSet(method, object, newValue);
    }
}
