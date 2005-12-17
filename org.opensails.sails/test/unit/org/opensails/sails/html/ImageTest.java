package org.opensails.sails.html;

import junit.framework.TestCase;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.SailsEventFixture;

public class ImageTest extends TestCase {

	public void testToString() {
		ISailsEvent event = SailsEventFixture.actionGet();
		Image image = new Image(event, "image.jpg");
		assertEquals("<img src=\"" + SailsEventFixture.getImagePath(event, "image.jpg") + "\" />", image.toString());
	}

	public void testToString_Alt() {
		ISailsEvent event = SailsEventFixture.actionGet();
		Image image = new Image(event, "image.jpg").alt("description");
		assertEquals("<img src=\"" + SailsEventFixture.getImagePath(event, "image.jpg") + "\" alt=\"description\" />", image.toString());
	}
}
