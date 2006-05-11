package org.opensails.sails.tester;

import org.opensails.rigging.ComponentResolver;
import org.opensails.rigging.IContainer;

public interface ITesterContainer extends IContainer {

    void inject(Class<?> key, ComponentResolver resolver);

    <T> void inject(Class<? super T> key, Class<T> implementation);

    <T> void inject(Class<? super T> key, T instance);

}