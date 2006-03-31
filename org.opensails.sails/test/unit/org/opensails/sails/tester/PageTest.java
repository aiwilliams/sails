package org.opensails.sails.tester;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.event.oem.ShamEvent;
import org.opensails.sails.util.NullPrintStream;
import org.opensails.viento.IBinding;

public class PageTest extends TestCase {
	public void testAssertContainsInOrder() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		event.write("one two three");
		Page page = new Page(event);

		page.assertContainsInOrder("two");
		page.assertContainsInOrder("one", "three");

		silenceSyserr();
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
		page.assertLayout("home/the layout");
		try {
			page.assertLayout("not it");
			throw new RuntimeException("Not the layout that was rendered");
		} catch (AssertionFailedError expected) {}
		try {
			page.assertLayout(null);
			throw new RuntimeException("Expected none, but there was one, so fail");
		} catch (AssertionFailedError expected) {}
	}

	public void testAssertRenders() throws Exception {
		ShamEvent event = SailsEventFixture.sham();
		Page page = new Page(event);
		page.assertRenders();

		event.getResponse().sendRedirect("somewhere");
		try {
			page.assertRenders();
			throw new RuntimeException("Redirected, so could not have rendered");
		} catch (AssertionFailedError expected) {}
	}

	public void testExposed() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		event.getContainer().instance(IBinding.class).put("mything", "you found it");
		Page page = new Page(event);
		assertEquals("you found it", page.exposed("mything").value);

		event.getResponse().sendRedirect("somewhere");
		try {
			page.exposed("mything");
			throw new RuntimeException("When redirected, nothing exposed is useful");
		} catch (AssertionFailedError expected) {}
	}

	public void testGetForm() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		Page page = new Page(event);
		assertNotNull(page.form());
	}

	public void testRedirectUrl() throws Exception {
		ShamEvent event = SailsEventFixture.sham();
		Page page = new Page(event);
		try {
			page.redirectUrl();
			throw new RuntimeException("If not redirected, should fail");
		} catch (AssertionFailedError expected) {}
		event.getResponse().sendRedirect("some location");
		page.redirectUrl().assertMatches("some location");
	}

	@Override
	protected void tearDown() throws Exception {
		restoreSyserr();
	}

	private void restoreSyserr() {
		System.setErr(System.err);
	}

	private void silenceSyserr() {
		System.setErr(new NullPrintStream());
	}
}
