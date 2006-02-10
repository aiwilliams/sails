package org.opensails.sails.tester;

import junit.framework.TestCase;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.RequestContainer;
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
		SailsTester tester = SailsTesterFixture.create();
		assertNotNull(tester.getConfiguration());
		assertNotNull(tester.getContainer());
	}

	public void testExceptions() {
		SailsTester tester = new SailsTester(ShamConfigurator.class);
		try {
			tester.get(TesterController.class, "exception");
			fail("Tester should allow controller exceptions to come through. This significantly eases development.");
		} catch (SailsException expected) {}
		try {
			tester.get(TesterComponent.class, "exception");
			fail("Tester should allow component exceptions to come through. This significantly eases development.");
		} catch (SailsException expected) {
			/*
			 * This works because any ExceptionEvent is processed by the current
			 * 'error' controller. Would it be nice to offer the offending
			 * IEventProcessingContext a chance to handle the failure?
			 */
		}
	}

	public void testGet() throws Exception {
		SailsTester tester = SailsTesterFixture.create();
		Page page = tester.get();
		assertTrue(page.url().matches("http://"));
		page.assertContains("Welcome to Sails");
	}

	public void testFollow_TestRedirectUrl() throws Exception {
		SailsTester tester = SailsTesterFixture.create();
		ShamHttpServletResponse response = response = new ShamHttpServletResponse();
		response.sendRedirect("http://google.com");
		Page page = tester.follow(new TestRedirectUrl(response));
		assertTrue(page.url().matches("http://google.com"));
	}
	
	public void testFollow_TestRedirectUrl_ToASailsController() {
		SailsTester tester = new SailsTester(ShamConfigurator.class);
		Page redirect = tester.get(TesterController.class, "redirect");
		Page otherAction = tester.follow(redirect.redirectUrl());
		otherAction.assertContains("Made it!");
	}

	public void testGetRequestContainer() {
		SailsTester tester = SailsTesterFixture.create();
		TestRequestContainer requestContainer = tester.getRequestContainer();
		assertNotNull("We can get it before we ever make a request", requestContainer);
		assertSame("Same until request is made", requestContainer, tester.getRequestContainer());
		IDo registeredBeforeGet = new IDo();
		requestContainer.register(registeredBeforeGet);
		Page pageOne = tester.get();
		assertSame(registeredBeforeGet, requestContainer.instance(IDo.class));
		assertSame("Same in page as was in tester before request", requestContainer, pageOne.container());

		TestRequestContainer subsequentRequestContainer = tester.getRequestContainer();
		assertNotSame("New container for preparing for next request. Old container can still be obtained from Page of last request", requestContainer, subsequentRequestContainer);
		Page pageTwo = tester.get();
		assertNull("Something registered in container of last event shouldn't exist in subsequent containers", subsequentRequestContainer.instance(IDo.class));
		assertNotSame(pageOne.container(), pageTwo.container());
	}

	public void testInject() {
		SailsTester tester = new SailsTester(ShamConfigurator.class);
		tester.inject(ILoveTesting.class, ReallyReallyIDo.class, ApplicationScope.REQUEST);
		assertEquals(ReallyReallyIDo.class, tester.getRequestContainer().instance(ILoveTesting.class).getClass());
		Page page = tester.get();
		assertEquals(ReallyReallyIDo.class, page.container().instance(ILoveTesting.class).getClass());
		assertEquals("Injections should stick around", ReallyReallyIDo.class, tester.getRequestContainer().instance(ILoveTesting.class).getClass());
	}

	public void testRedirect() {
		SailsTester tester = new SailsTester(ShamConfigurator.class);
		Page page = tester.get(TesterController.class, "redirect");
		page.redirectUrl().assertMatches("tester/otherAction");
	}

	public static interface ILoveTesting {}

	public static class ReallyIDo implements ILoveTesting {}

	public static class ReallyReallyIDo implements ILoveTesting {}

	public static class IDo implements ILoveTesting {}

	public static class ShamConfigurator extends BaseConfigurator {
		@Override
		public void configure(ISailsEvent event, RequestContainer eventContainer) {
			eventContainer.register(ILoveTesting.class, ReallyIDo.class);
		}
	}
}
