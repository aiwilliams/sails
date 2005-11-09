package org.opensails.sails.controller.oem;

import junit.framework.TestCase;

import org.opensails.sails.http.ContentType;
import org.opensails.sails.oem.SailsEventFixture;

public class TemplateActionResultTest extends TestCase {
	public void testInitialize() throws Exception {
		TemplateActionResult result = new TemplateActionResult(SailsEventFixture.actionGet());
		assertEquals(ContentType.TEXT_HTML.toHttpValue(), result.getEvent().getResponse().getContentType());
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
