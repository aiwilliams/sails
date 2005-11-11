package org.opensails.rigging;

import java.lang.reflect.Constructor;

public class ComponentImplementation implements ComponentResolver {
    protected Class theClass;
    protected Object instance;
    protected SimpleContainer container;

    public ComponentImplementation(Class theClass, SimpleContainer container) {
        this.theClass = theClass;
        this.container = container;
    }

    public Object instance() {
        if (instance == null) return instance = instantiate();
        return instance;
    }

    @SuppressWarnings("unchecked")
	protected Object instantiate() {
        Constructor constructor = findConstructor();
        if (constructor == null) return null;
        Class[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++)
            parameters[i] = container.instance(parameterTypes[i]);
        try {
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    protected boolean canSatisfy(Constructor constructor) {
        boolean canSatisfy = true;
        for (Class type : constructor.getParameterTypes())
            if (!container.contains(type)) canSatisfy = false;
        return canSatisfy;
    }

	public boolean isInstantiated() {
		return instance != null;
	}
}
