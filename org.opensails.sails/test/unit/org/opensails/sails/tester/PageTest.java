package org.opensails.sails.tester;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.event.oem.ShamEvent;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.HtmlFormFixture;
import org.opensails.viento.IBinding;

public class PageTest extends TestCase {
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

	public void testAssertLayout() throws Exception {
		ShamEvent event = SailsEventFixture.sham();
		TemplateActionResult templateActionResult = new TemplateActionResult(event);
		templateActionResult.setLayout("the layout");
		event.getContainer().register(templateActionResult);
		Page page = new Page(event);
		page.assertLayout("the layout");
		try {
			page.assertLayout("not it");
			throw new RuntimeException("Not the layout that was rendered");
		} catch (AssertionFailedError expected) {}
		try {
			page.assertLayout(null);
			throw new RuntimeException("Expected none, but there was one, so fail");
		} catch (AssertionFailedError expected) {}
	}

	public void testExposed() {
		GetEvent event = SailsEventFixture.actionGet();
		event.getContainer().instance(IBinding.class).put("mything", "you found it");
		Page page = new Page(event);
		assertEquals("you found it", page.exposed("mything").value);
	}

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
}
