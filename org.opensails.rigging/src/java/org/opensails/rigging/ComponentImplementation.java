package org.opensails.rigging;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ComponentImplementation implements ComponentResolver {
    protected IContainer container;
    protected Object instance;
    protected Class theClass;

    public ComponentImplementation(Class theClass, IContainer container) {
        this.theClass = theClass;
        this.container = container;
    }

    public ComponentResolver cloneFor(SimpleContainer container) {
        return new ComponentImplementation(theClass, container);
    }

    public Object instance() {
        if (instance == null) return instance = instantiate();
        return instance;
    }

    public boolean isInstantiated() {
        return instance != null;
    }

    public Class<?> type() {
        return theClass;
    }

    @SuppressWarnings("unchecked")
    protected <T extends Annotation> T annotation(Class<T> annotationType, Constructor constructor, int parameterIndex) {
        for (Annotation annotation : constructor.getParameterAnnotations()[parameterIndex])
            if (annotation.annotationType() == annotationType) return (T) annotation;
        return null;
    }

    protected boolean canSatisfy(Constructor constructor) {
        boolean canSatisfy = true;
        Class[] parameterTypes = constructor.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            WhenNotInstantiated whenNotInstantiated = annotation(WhenNotInstantiated.class, constructor, i);
            if (!container.contains(parameterTypes[i]) && (whenNotInstantiated == null || findConstructor(whenNotInstantiated.value()) == null)) canSatisfy = false;
        }
        return canSatisfy;
    }

    protected Constructor findConstructor() {
        Constructor[] constructors = theClass.getConstructors();
        Constructor greedyless = null;
        Constructor greediest = null;
        for (Constructor constructor : constructors) {
            if (greedyless == null || constructor.getParameterTypes().length < greedyless.getParameterTypes().length) greedyless = constructor;
            if ((greediest == null || constructor.getParameterTypes().length > greediest.getParameterTypes().length) && canSatisfy(constructor)) greediest = constructor;
        }
        if (greediest == null) throw new UnsatisfiableDependenciesException(greedyless);
        return greediest;
    }

    // Duplication. Can't return two things. Please refactor.
    protected Constructor findConstructor(Class theClass) {
        Constructor[] constructors = theClass.getConstructors();
        Constructor greedyless = null;
        Constructor greediest = null;
        for (Constructor constructor : constructors) {
            if (greedyless == null || constructor.getParameterTypes().length < greedyless.getParameterTypes().length) greedyless = constructor;
            if ((greediest == null || constructor.getParameterTypes().length > greediest.getParameterTypes().length) && canSatisfy(constructor)) greediest = constructor;
        }
        return greediest;
    }

    @SuppressWarnings("unchecked")
    protected Object instantiate() {
        Constructor constructor = findConstructor();
        if (constructor == null) return null;
        Class[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            WhenNotInstantiated whenNotInstantiated = annotation(WhenNotInstantiated.class, constructor, i);
            if (whenNotInstantiated != null && !container.resolver(parameterTypes[i]).isInstantiated()) parameters[i] = container.instance(whenNotInstantiated.value(), whenNotInstantiated.value());
            else parameters[i] = container.instance(parameterTypes[i]);
        }
        try {
            Object newInstance = constructor.newInstance(parameters);
            container.notifyInstantiationListeners(theClass, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
