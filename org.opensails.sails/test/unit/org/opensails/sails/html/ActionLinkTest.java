package org.opensails.sails.html;

import junit.framework.TestCase;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.url.UrlType;

public class ActionLinkTest extends TestCase {
	public void testImage() throws Exception {
		ISailsEvent event = SailsEventFixture.actionGet();
		ActionLink link = new ActionLink(event);
		link.controller("controller").action("action");
		ImageLink imageLink = link.image("myImage");
		ImageLink expected = new ImageLink(event, link.getUrl(), event.resolve(UrlType.IMAGE, "myImage"));
		assertEquals(expected, imageLink);
	}

	public void testRender() {
		ISailsEvent event = SailsEventFixture.actionGet();
		ActionLink link = new ActionLink(event).controller("controller").action("action");
		assertEquals("<a href=\"" + event.resolve(UrlType.CONTROLLER, "controller/action") + "\">controller/action</a>", link.renderThyself());
	}

	public void testRender_NoAction() {
		ISailsEvent event = SailsEventFixture.actionGet();
		ActionLink link = new ActionLink(event).controller("controller");
		assertEquals("<a href=\"" + event.resolve(UrlType.CONTROLLER, "controller") + "\">controller</a>", link.renderThyself());
	}

	public void testRender_NoController() {
		ISailsEvent event = SailsEventFixture.actionGet("originalController", "originalAction");
		ActionLink link = new ActionLink(event).action("action");
		assertEquals("<a href=\"" + event.resolve(UrlType.CONTROLLER, "originalController/action") + "\">originalController/action</a>", link.renderThyself());
	}

	public void testRender_Secure() {
		ISailsEvent event = SailsEventFixture.actionGet();
		ActionLink link = new ActionLink(event).controller("controller").secure();
		assertTrue(link.renderThyself().contains("https://"));
	}

	public void testRender_Text() {
		ISailsEvent event = SailsEventFixture.actionGet();
		ActionLink link = new ActionLink(event).controller("controller").action("action").text("text");
		assertEquals("<a href=\"" + event.resolve(UrlType.CONTROLLER, "controller/action") + "\">text</a>", link.renderThyself());
	}
}
