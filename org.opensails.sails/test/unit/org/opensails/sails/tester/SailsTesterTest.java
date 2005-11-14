package org.opensails.sails.tester;

import junit.framework.TestCase;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.BaseConfigurator;

public class SailsTesterTest extends TestCase {
	/**
	 * When you use a simple SailsTester, it will boot using all the defaults.
	 */
	public void testConstructor() throws Exception {
		SailsTester tester = new SailsTester();
		assertNotNull(tester.getConfiguration());
		assertNotNull(tester.getContainer());
	}

	public void testGet() throws Exception {
		SailsTester tester = new SailsTester();
		Page page = tester.get();
		assertTrue(page.getUrl().matches("http://"));
		page.assertContains("Welcome to Sails");
	}

	public void testInject() {
		SailsTester tester = new SailsTester(ShamConfigurator.class);
		tester.inject(ILoveTesting.class, ReallyReallyIDo.class, ApplicationScope.REQUEST);
		assertEquals(ReallyReallyIDo.class, tester.getRequestContainer().instance(ILoveTesting.class).getClass());
		Page page = tester.get();
		assertEquals(ReallyReallyIDo.class, page.getContainer().instance(ILoveTesting.class).getClass());
	}

	public void testPost() throws Exception {
		SailsTester tester = new SailsTester(UnitTestConfigurator.class);
		FormFields formFields = new FormFields();
		formFields.setValue("firstName", "bobby");
		Page page = tester.post(formFields);
		page.assertContains("you entered bobby");
	}

	public static class ShamConfigurator extends BaseConfigurator {
		@Override
		public void configure(ISailsEvent event, RequestContainer eventContainer) {
			eventContainer.register(ILoveTesting.class, ReallyIDo.class);
		}
	}

	public static interface ILoveTesting {}

	public static class ReallyIDo implements ILoveTesting {}

	public static class ReallyReallyIDo implements ILoveTesting {}
}
