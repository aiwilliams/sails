package org.opensails.sails.tester;

import junit.framework.TestCase;

import org.opensails.sails.form.FormFields;

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

	public void testPost() throws Exception {
		SailsTester tester = new SailsTester(UnitTestConfigurator.class);
		FormFields formFields = new FormFields();
		formFields.setValue("firstName", "bobby");
		Page page = tester.post(formFields);
		page.assertContains("you entered bobby");
	}
}
