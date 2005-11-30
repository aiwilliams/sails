package org.opensails.sails.tester;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.HtmlFormFixture;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;

public class PageTest extends TestCase {
	public void testGetForm() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		event.write("<form></form>");
		Page page = new Page(event);
		assertNotNull(page.form());

		event = SailsEventFixture.actionGet();
		page = new Page(event);
		try {
			page.form();
			throw new RuntimeException("If there is no form in the source, or an HtmlForm in the container, invalid");
		} catch (AssertionFailedError expected) {}

		event.getContainer().register(HtmlForm.class, HtmlFormFixture.create());
		assertNotNull(page.form());
	}

	public void testAssertLayout() throws Exception {
		fail("note to self: mocks are good");
	}
	
	public void testAssertContainsInOrder() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		event.write("one two three");
		Page page = new Page(event);
		
		page.assertContainsInOrder("two");
		page.assertContainsInOrder("one", "three");
		
		try {
			page.assertContainsInOrder("two", "one");
			throw new RuntimeException();
		} catch (AssertionFailedError expected) {}

		try {
			page.assertContainsInOrder("two", "four");
			throw new RuntimeException();
		} catch (AssertionFailedError expected) {}
	}
}
