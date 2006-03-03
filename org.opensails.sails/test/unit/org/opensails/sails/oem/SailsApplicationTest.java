package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.rigging.Disposable;
import org.opensails.rigging.Startable;
import org.opensails.rigging.Stoppable;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.tester.servletapi.ShamServletConfig;
import org.opensails.sails.tester.servletapi.ShamServletContext;

public class SailsApplicationTest extends TestCase {
    public void testInit() throws Exception {
        ShamServletContext context = new ShamServletContext();
        context.setInitParameter("some.property.somewhere", "from context");
        context.setInitParameter("someother.property.somewhere", "from context");
        context.setInitParameter("someotheragain.property.somewhere", "from context");

        ShamServletConfig servletConfig = new ShamServletConfig(context);
        servletConfig.setInitParameter("some.property.somewhere", "from servlet");
        servletConfig.setInitParameter("someother.property.somewhere", "from servlet");
        servletConfig.setInitParameter(ISailsApplicationConfigurator.class.getName(), ShamApplicationConfigurator.class.getName());

        System.getProperties().setProperty("some.property.somewhere", "from system");

        SailsApplication application = new SailsApplication();
        application.init(servletConfig);

        assertEquals("from system", application.getConfiguration().getProperty("some.property.somewhere"));
        assertEquals("from servlet", application.getConfiguration().getProperty("someother.property.somewhere"));
        assertEquals("from context", application.getConfiguration().getProperty("someotheragain.property.somewhere"));

        assertTrue(((ShamApplicationConfigurator) application.configurator).containerStarted);
    }

    public void testLifecycleNotifications() throws Exception {
        SailsFunctionalTester t = new SailsFunctionalTester();
        MyListener myListener = new MyListener();
        t.inject(MyListener.class, myListener, ApplicationScope.SERVLET);
        assertFalse(myListener.started);
        assertFalse(myListener.stopped);
        assertFalse(myListener.disposed);
        t.get();
        assertTrue(myListener.started);
        assertFalse(myListener.disposed);
        myListener.started = false;
        t.get();
        assertFalse(myListener.started);
        assertFalse(myListener.stopped);
        assertFalse(myListener.disposed);
        t.destroy();
        assertTrue(myListener.stopped);
        assertTrue(myListener.disposed);
    }

    public class MyListener implements Startable, Stoppable, Disposable {
        boolean disposed;
        boolean started;
        boolean stopped;

        public void dispose() {
            disposed = true;
        }

        public void start() {
            started = true;
        }

        public void stop() {
            stopped = true;
        }
    }
}
