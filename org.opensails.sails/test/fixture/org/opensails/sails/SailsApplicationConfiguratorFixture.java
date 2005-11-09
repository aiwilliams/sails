package org.opensails.sails;

import java.lang.reflect.Method;

import javax.servlet.ServletConfig;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.oem.SailsApplication;
import org.opensails.sails.tester.servletapi.ShamServletConfig;
import org.opensails.sails.util.ClassInstanceAccessor;

public class SailsApplicationConfiguratorFixture {
    /**
     * Configure a SailsApplication for testing using an instance of
     * {@link BaseConfigurator}
     * 
     * @param application
     */
    public static void configure(IConfigurableSailsApplication application) {
        configure(application, new BaseConfigurator());
    }

    /**
     * Configure a SailsApplication for testing, allowing the use of a specific
     * {@link ISailsApplicationConfigurator} instance.
     * 
     * @param application
     */
    public static void configure(IConfigurableSailsApplication application, ISailsApplicationConfigurator configurator) {
        configure(application, configurator, new ShamServletConfig());
    }

    /**
     * Configure a SailsApplication for testing, allowing the use of a specific
     * {@link ISailsApplicationConfigurator} instance and ServletConfig.
     * 
     * @param application
     */
    public static void configure(IConfigurableSailsApplication application, ISailsApplicationConfigurator configurator, ServletConfig config) {
        ClassInstanceAccessor accessor = new ClassInstanceAccessor(SailsApplication.class, true);
        // config is on GenericServlet
        accessor.setProperty(application, "config", config);

        configurator.configure(application);

        try {
            Method start = SailsApplication.class.getDeclaredMethod("startApplication");
            start.setAccessible(true);
            start.invoke(application, ArrayUtils.EMPTY_OBJECT_ARRAY);
        } catch (Exception e) {
            Assert.fail("Could not configure " + application + ": expected to have method startApplication()");
        }
    }

}
