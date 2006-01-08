package org.opensails.sails.action.oem;

import junit.framework.TestCase;

import org.opensails.sails.action.oem.PartialActionResult;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.http.ContentType;

public class PartialActionResultTest extends TestCase {
	public void testInitialize() throws Exception {
		PartialActionResult result = new PartialActionResult(SailsEventFixture.actionGet());
		assertEquals(ContentType.TEXT_HTML.value(), result.getEvent().getResponse().getContentType());
	}
}
