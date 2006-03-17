package org.opensails.sails.tester;

import junit.framework.TestCase;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.tester.components.TesterComponent;
import org.opensails.sails.tester.controllers.TesterController;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class SailsTesterTest extends TestCase {
    /**
     * When you use a simple SailsTester, it will boot using all the defaults.
     */
    public void testConstructor() throws Exception {
        SailsTester t = new SailsTester(BaseConfigurator.class);
        assertNotNull(t.getConfiguration());
        assertNotNull(t.getContainer());
    }

    public void testExceptions() {
        SailsTester t = new SailsTester(ShamConfigurator.class);
        try {
            t.get(TesterController.class, "exception");
            fail("Tester should allow controller exceptions to come through. This significantly eases development.");
        } catch (SailsException expected) {}
        try {
            t.get(TesterComponent.class, "exception");
            fail("Tester should allow component exceptions to come through. This significantly eases development.");
        } catch (SailsException expected) {
            /*
             * This works because any ExceptionEvent is processed by the current
             * 'error' controller. Would it be nice to offer the offending
             * IEventProcessingContext a chance to handle the failure?
             */
        }
    }

    public void testFollow_TestRedirectUrl() throws Exception {
        SailsTester t = new SailsTester(BaseConfigurator.class);
        ShamHttpServletResponse response = response = new ShamHttpServletResponse();
        response.sendRedirect("http://google.com");
        Page page = t.follow(new TestRedirectUrl(response));
        assertTrue(page.url().matches("http://google.com"));
    }

    public void testFollow_TestRedirectUrl_ToASailsController() {
        SailsTester t = new SailsTester(ShamConfigurator.class);
        Page redirect = t.get(TesterController.class, "redirect");
        Page otherAction = t.follow(redirect.redirectUrl());
        otherAction.assertContains("Made it!");
    }

    public void testGet() throws Exception {
        SailsTester t = new SailsTester(BaseConfigurator.class);
        Page page = t.get();
        assertTrue(page.url().matches("http://"));
        page.assertContains("Welcome to Sails");
    }

    public void testGetRequestContainer() {
        SailsTester t = new SailsTester(BaseConfigurator.class);
        TestRequestContainer requestContainer = t.getRequestContainer();
        assertNotNull("We can get it before we ever make a request", requestContainer);
        assertSame("Same until request is made", requestContainer, t.getRequestContainer());
        IDo registeredBeforeGet = new IDo();
        requestContainer.register(registeredBeforeGet);
        Page pageOne = t.get();
        assertSame(registeredBeforeGet, requestContainer.instance(IDo.class));
        assertSame("Same in page as was in tester before request", requestContainer, pageOne.container());

        TestRequestContainer subsequentRequestContainer = t.getRequestContainer();
        assertNotSame("New container for preparing for next request. Old container can still be obtained from Page of last request", requestContainer, subsequentRequestContainer);
        Page pageTwo = t.get();
        assertNull("Something registered in container of last event shouldn't exist in subsequent containers", subsequentRequestContainer.instance(IDo.class));
        assertNotSame(pageOne.container(), pageTwo.container());
    }

    public void testInject_ApplicationScope() throws Exception {
        SailsTester t = new SailsTester(BaseConfigurator.class);
        t.inject(ILoveTesting.class, ReallyReallyIDo.class, ApplicationScope.SERVLET);
        ILoveTesting applicationScopeInstance = t.getContainer().instance(ILoveTesting.class);
        assertEquals(ReallyReallyIDo.class, applicationScopeInstance.getClass());
        assertSame(applicationScopeInstance, t.getRequestContainer().instance(ILoveTesting.class));
        Page page = t.get();
        assertSame(applicationScopeInstance, page.container().instance(ILoveTesting.class));
    }

    public void testInject_RequestScope() {
        SailsTester t = new SailsTester(ShamConfigurator.class);
        t.inject(ILoveTesting.class, ReallyReallyIDo.class, ApplicationScope.REQUEST);
        assertEquals(ReallyReallyIDo.class, t.getRequestContainer().instance(ILoveTesting.class).getClass());
        Page page = t.get();
        assertEquals(ReallyReallyIDo.class, page.container().instance(ILoveTesting.class).getClass());
        assertEquals("Injections should stick around", ReallyReallyIDo.class, t.getRequestContainer().instance(ILoveTesting.class).getClass());
    }

    public void testRedirect() {
        SailsTester t = new SailsTester(ShamConfigurator.class);
        Page page = t.get(TesterController.class, "redirect");
        page.redirectUrl().assertMatches("tester/otherAction");
    }

    public static class IDo implements ILoveTesting {}

    public static interface ILoveTesting {}

    public static class ReallyIDo implements ILoveTesting {}

    public static class ReallyReallyIDo implements ILoveTesting {}

    public static class ShamConfigurator extends BaseConfigurator {
        @Override
        public void configure(ISailsEvent event, IEventContextContainer eventContainer) {
            eventContainer.register(ILoveTesting.class, ReallyIDo.class);
        }
    }
}
