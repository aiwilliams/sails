package org.opensails.sails.tester;

import junit.framework.TestCase;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.BaseConfigurator;

public class SailsTesterTest extends TestCase {
	/**
	 * When you use a simple SailsTester, it will boot using all the defaults.
	 */
	public void testConstructor() throws Exception {
		SailsTester tester = SailsTesterFixture.create();
		assertNotNull(tester.getConfiguration());
		assertNotNull(tester.getContainer());
	}

	public void testGet() throws Exception {
		SailsTester tester = SailsTesterFixture.create();
		Page page = tester.get();
		assertTrue(page.url().matches("http://"));
		page.assertContains("Welcome to Sails");
	}

	public void testGetRequestContainer() {
		SailsTester tester = SailsTesterFixture.create();
		TestRequestContainer requestContainer = tester.getRequestContainer();
		assertNotNull("We can get it before we ever make a request", requestContainer);
		assertSame("Same until request is made", requestContainer, tester.getRequestContainer());
		Page pageOne = tester.get();
		assertSame("Same in page as was in tester before request", requestContainer, pageOne.container());
		assertNotSame("New container for preparing for next request. Old container can still be obtained from Page of last request", requestContainer, tester.getRequestContainer());
		Page pageTwo = tester.get();
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

	public static interface ILoveTesting {}

	public static class ReallyIDo implements ILoveTesting {}

	public static class ReallyReallyIDo implements ILoveTesting {}

	public static class ShamConfigurator extends BaseConfigurator {
		@Override
		public void configure(ISailsEvent event, RequestContainer eventContainer) {
			eventContainer.register(ILoveTesting.class, ReallyIDo.class);
		}
	}
}
