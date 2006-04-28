package org.opensails.sails.html;

import junit.framework.TestCase;

import org.opensails.sails.url.ExternalUrl;

public class ImageTest extends TestCase {

	public void testRender() {
		Image image = new Image(new ExternalUrl("http://host/image.jpg"));
		assertEquals("<img src=\"http://host/image.jpg\" />", image.renderThyself());
	}

	public void testRender_Alt() {
		Image image = new Image(new ExternalUrl("http://host/image.jpg")).alt("description");
		assertEquals("<img src=\"http://host/image.jpg\" alt=\"description\" />", image.renderThyself());
	}
}
