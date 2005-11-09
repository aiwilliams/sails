package org.opensails.sails.mixins;

import java.util.Arrays;

import junit.framework.TestCase;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.mixins.UrlforMixin;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.servletapi.ShamHttpServletResponse;
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.url.UrlType;

public class UrlForMixinTest extends TestCase {
	public void testAction() {
		GetEvent event = SailsEventFixture.actionGet("mycontroller", "myaction");
		UrlforMixin tool = new UrlforMixin(event);
		String expected = new ActionUrl(event, "mycontroller", "anotheraction").render();
		assertEquals(expected, tool.action("anotheraction").render());
		assertEncodedURL(expected, event);
	}

	public void testAction_WithController() {
		ISailsEvent event = SailsEventFixture.actionGet("mycontroller", "myaction");
		UrlforMixin tool = new UrlforMixin(event);
		String expectedUrl = new ActionUrl(event, "anothercontroller", "anotheraction").render();
		assertEquals(expectedUrl, tool.action("anothercontroller", "anotheraction").render());
		assertEncodedURL(expectedUrl, event);
	}

	public void testAction_WithParameters() {
		ISailsEvent event = SailsEventFixture.actionGet("mycontroller", "myaction");
		UrlforMixin tool = new UrlforMixin(event);
		ActionUrl expected = new ActionUrl(event, "mycontroller", "anotheraction");
		expected.setParameters("paramOne", "paramTwo");
		String expectedURL = expected.render();
		assertEquals(expectedURL, tool.action("anotheraction", Arrays.asList(new Object[] { "paramOne", "paramTwo" })).render());
		assertEncodedURL(expectedURL, event);
	}

	public void testController() throws Exception {
		ISailsEvent event = SailsEventFixture.actionGet("mycontroller", "myaction");
		UrlforMixin tool = new UrlforMixin(event);
		ActionUrl expected = new ActionUrl(event);
		expected.setController("mycontroller");
		String expectedURL = expected.render();
		assertEquals(expectedURL, tool.controller().render());
		assertEncodedURL(expectedURL, event);
	}

	public void testController_Specified() {
		ISailsEvent event = SailsEventFixture.actionGet("mycontroller", "myaction");
		UrlforMixin tool = new UrlforMixin(event);
		ActionUrl expected = new ActionUrl(event);
		expected.setController("anothercontroller");
		String expectedURL = expected.render();
		assertEquals(expectedURL, tool.controller("anothercontroller").render());
		assertEncodedURL(expectedURL, event);
	}

	public void testImage() {
		ISailsEvent event = SailsEventFixture.actionGet("mycontroller", "myaction");
		UrlforMixin tool = new UrlforMixin(event);
		String expectedURL = event.resolve(UrlType.IMAGE, "my.jpg").render();
		assertEquals(expectedURL, tool.image("my.jpg").render());
		assertNotEncodedURL(expectedURL, event);
	}

	public void testScript() {
		ISailsEvent event = SailsEventFixture.actionGet("mycontroller", "myaction");
		UrlforMixin tool = new UrlforMixin(event);
		String expectedURL = event.resolve(UrlType.SCRIPT, "my.js").render();
		assertEquals(expectedURL, tool.script("my.js").render());
		assertNotEncodedURL(expectedURL, event);
	}

	public void testStyle() {
		ISailsEvent event = SailsEventFixture.actionGet("mycontroller", "myaction");
		UrlforMixin tool = new UrlforMixin(event);
		String expectedURL = event.resolve(UrlType.STYLE, "my.css").render();
		assertEquals(expectedURL, tool.style("my.css").render());
		assertNotEncodedURL(expectedURL, event);
	}

	protected void assertEncodedURL(String url, ISailsEvent event) {
		ShamHttpServletResponse response = (ShamHttpServletResponse) event.getResponse();
		assertTrue("Generated URL {" + url + "} should be encoded", response.wasUrlEncoded(url));
	}

	protected void assertNotEncodedURL(String url, ISailsEvent event) {
		ShamHttpServletResponse response = (ShamHttpServletResponse) event.getResponse();
		assertFalse("Generated URL {" + url + "} should not be encoded", response.wasUrlEncoded(url));
	}
}
