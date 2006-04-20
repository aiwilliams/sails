package org.opensails.sails.html;

import junit.framework.TestCase;

import org.opensails.sails.url.ExternalUrl;

public class ImageTest extends TestCase {

	public void testRender() {
		Image image = new Image(new ExternalUrl("image.jpg"));
		assertEquals("<img src=\"image.jpg\" />", image.renderThyself());
	}

	public void testRender_Alt() {
		Image image = new Image(new ExternalUrl("image.jpg")).alt("description");
		assertEquals("<img src=\"image.jpg\" alt=\"description\" />", image.renderThyself());
	}
}
