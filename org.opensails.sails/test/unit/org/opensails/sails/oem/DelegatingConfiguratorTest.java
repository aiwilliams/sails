package org.opensails.sails.oem;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import junit.framework.TestCase;

public class DelegatingConfiguratorTest extends TestCase {
    public void testAllMethodsOverridden() throws Exception {
        for (Method method : BaseConfigurator.class.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
                assertNotNull(DelegatingConfigurator.class.getDeclaredMethod(method.getName(), (Class[]) method.getParameterTypes()));
            }
        }
    }
}
