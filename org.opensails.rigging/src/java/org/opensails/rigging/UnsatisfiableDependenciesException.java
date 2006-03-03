package org.opensails.rigging;

import java.lang.reflect.Constructor;

/**
 * Thrown by a container when it cannot satisfy the dependencies of a component.
 */
public class UnsatisfiableDependenciesException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected final Constructor greedyless;

    public UnsatisfiableDependenciesException(Constructor greedyless) {
        super("Least greedy constructor: " + greedyless);
        this.greedyless = greedyless;
    }

    public Class<?>[] getMinimumDependencies() {
        return greedyless.getParameterTypes();
    }
}
