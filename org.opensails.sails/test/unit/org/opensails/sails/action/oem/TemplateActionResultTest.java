package org.opensails.sails.action.oem;

import junit.framework.TestCase;

import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.event.oem.ShamEvent;
import org.opensails.sails.http.ContentType;

public class TemplateActionResultTest extends TestCase {
	public void testInitialize() throws Exception {
		TemplateActionResult result = new TemplateActionResult(SailsEventFixture.actionGet());
		assertEquals(ContentType.TEXT_HTML.toHttpValue(), result.getEvent().getResponse().getContentType());
	}

	public void testSetTemplate() throws Exception {
		ShamEvent event = SailsEventFixture.sham();
		TemplateActionResult result = new TemplateActionResult(event);
		assertEquals(String.format("%s/%s", event.getProcessorName(), event.getActionName()), result.getIdentifier());

		result.setTemplate("test/someaction");
		assertEquals(String.format("%s/%s", "test", "someaction"), result.getIdentifier());
	}

	public void testHasLayout() throws Exception {
		TemplateActionResult result = new TemplateActionResult(SailsEventFixture.sham());
		assertFalse(result.hasLayout());

		result.setLayout("hello");
		assertTrue(result.hasLayout());

		result.setLayout(null);
		assertFalse(result.hasLayout());
	}
}
