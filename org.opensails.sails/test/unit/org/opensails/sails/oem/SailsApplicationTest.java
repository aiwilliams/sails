package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.SailsApplicationConfiguratorFixture;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;
import org.opensails.sails.tester.servletapi.ShamServletConfig;
import org.opensails.sails.tester.servletapi.ShamServletContext;

public class SailsApplicationTest extends TestCase {
    private ISailsEvent dispatchedEvent;

    public void testDoGet_DoPost() throws Exception {
        SailsApplication application = new SailsApplication();
        SailsApplicationConfiguratorFixture.configure(application, new BaseConfigurator() {
            @Override
            public Dispatcher installDispatcher(IConfigurableSailsApplication application, ScopedContainer container) {
                Dispatcher dispatcher = new Dispatcher(null, null, null, null) {
                    @Override
                    public void dispatch(GetEvent event) {
                        dispatchedEvent = event;
                    }

                    @Override
                    public void dispatch(PostEvent event) {
                        dispatchedEvent = event;
                    }
                };
                application.setDispatcher(dispatcher);
                return dispatcher;
            }
        });
        application.service(new ShamHttpServletRequest(ShamHttpServletRequest.GET), new ShamHttpServletResponse());
        assertEquals(GetEvent.class, dispatchedEvent.getClass());
        application.service(new ShamHttpServletRequest(ShamHttpServletRequest.POST), new ShamHttpServletResponse());
        assertEquals(PostEvent.class, dispatchedEvent.getClass());
    }

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
}
