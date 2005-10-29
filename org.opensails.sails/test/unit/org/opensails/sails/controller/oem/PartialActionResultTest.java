package org.opensails.sails.controller.oem;

import junit.framework.TestCase;

import org.opensails.sails.http.ContentType;
import org.opensails.sails.oem.SailsEventFixture;

public class PartialActionResultTest extends TestCase {
	public void testInitialize() throws Exception {
		PartialActionResult result = new PartialActionResult(SailsEventFixture.actionGet());
		assertEquals(ContentType.TEXT_HTML.toHttpValue(), result.getEvent().getResponse().getContentType());
	}
}
