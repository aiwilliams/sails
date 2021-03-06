package org.opensails.sails.oem;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.event.oem.ShamEvent;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;
import org.opensails.sails.tester.util.CollectionAssert;
import org.opensails.sails.util.Quick;

public class FlashTest extends TestCase {
	protected ISailsEvent event;
	protected Flash flash;

	public void testContainsKey() throws Exception {
		assertTrue(flash.containsKey("firstStuff"));
		flash.beginDispatch(event);
		assertTrue(flash.containsKey("firstStuff"));
		flash.put("more", "stuff");
		assertTrue(flash.containsKey("more"));
		flash.endDispatch(event);
		assertFalse(flash.containsKey("firstStuff"));
		assertTrue(flash.containsKey("more"));
	}

	public void testContainsValue() throws Exception {
		assertTrue(flash.containsValue("firstStuffValue"));
		flash.beginDispatch(event);
		assertTrue(flash.containsValue("firstStuffValue"));
		flash.put("more", "stuff");
		assertTrue(flash.containsValue("stuff"));
		flash.endDispatch(event);
		assertFalse(flash.containsValue("firstStuffValue"));
		assertTrue(flash.containsValue("stuff"));
	}

	public void testGet() {
		assertEquals("firstStuffValue", flash.get("firstStuff"));
		assertEquals("Second access, value should still exist", "firstStuffValue", flash.get("firstStuff"));

		flash.beginDispatch(event);
		assertEquals("We have marked the beginning of a request cycle - values still availble", "firstStuffValue", flash.get("firstStuff"));

		putAfterBeginRequest();
		assertEquals("thirdStuffValue", flash.get("thirdStuff"));
		assertEquals("newSecondStuffValue", flash.get("secondStuff"));

		flash.endDispatch(event);
		assertNull("The old stuff should be gone", flash.get("firstStuff"));
		assertEquals("The new stuff should still be available", "thirdStuffValue", flash.get("thirdStuff"));
		assertEquals("A value was added with same key as expired value - make sure it's kept", "newSecondStuffValue", flash.get("secondStuff"));
	}

	public void testIsEmpty() throws Exception {
		assertFalse(flash.isEmpty());
		flash.beginDispatch(event);
		assertFalse(flash.isEmpty());
		flash.put("new", "value");
		flash.endDispatch(event);
		assertFalse(flash.isEmpty());

		flash.beginDispatch(event);
		flash.endDispatch(event);
		assertTrue(flash.isEmpty());
	}

	public void testKeySet() throws Exception {
		CollectionAssert.containsOnly(Quick.list("firstStuff", "secondStuff"), flash.keySet());
		flash.beginDispatch(event);
		CollectionAssert.containsOnly(Quick.list("firstStuff", "secondStuff"), flash.keySet());
		putAfterBeginRequest();
		CollectionAssert.containsOnly(Quick.list("firstStuff", "secondStuff", "thirdStuff"), flash.keySet());
		flash.endDispatch(event);
		CollectionAssert.containsOnly(Quick.list("secondStuff", "thirdStuff"), flash.keySet());
	}

	public void testPutAll() throws Exception {
		Map<Object, Object> more = new HashMap<Object, Object>();
		more.put("moreOne", "moreOneValue");
		more.put("moreTwo", "moreTwoValue");
		flash.putAll(more);
		assertEquals("moreOneValue", flash.get("moreOne"));
		assertEquals("moreTwoValue", flash.get("moreTwo"));

		flash.beginDispatch(event);
		flash.put("moreTwo", "newMoreTwoValue");
		assertEquals("newMoreTwoValue", flash.get("moreTwo"));

		flash.endDispatch(event);
		assertNull(flash.get("moreOne"));
		assertEquals("newMoreTwoValue", flash.get("moreTwo"));

	}

	public void testSessionCreated() throws Exception {
		ISailsEvent eventOne = SailsEventFixture.sham();
		Flash eventOneRequestFlash = Flash.load(eventOne.getRequest(), eventOne.getSession(false));

		ISailsEvent eventTwo = SailsEventFixture.sham();
		Flash eventTwoRequestFlash = Flash.load(eventTwo.getRequest(), eventTwo.getSession(false));

		HttpSession eventOneSession = eventOne.getSession(true);
		eventTwoRequestFlash.sessionCreated(eventOne, eventOneSession);
		assertNull("Should not place himself in session where he is not in request of event", eventOneSession.getAttribute(Flash.KEY));

		eventOneRequestFlash.sessionCreated(eventOne, eventOneSession);
		assertSame(eventOneRequestFlash, eventOneSession.getAttribute(Flash.KEY));
	}

	public void testSize() throws Exception {
		assertEquals(2, flash.size());
		flash.beginDispatch(event);
		assertEquals(2, flash.size());
		putAfterBeginRequest();
		assertEquals(3, flash.size());
		flash.endDispatch(event);
		assertEquals(2, flash.size());
	}

	public void testValues() {
		CollectionAssert.containsOnly(Quick.list("firstStuffValue", "secondStuffValue"), flash.values());
		flash.beginDispatch(event);
		CollectionAssert.containsOnly(Quick.list("firstStuffValue", "secondStuffValue"), flash.values());
		putAfterBeginRequest();
		CollectionAssert.containsOnly(Quick.list("firstStuffValue", "newSecondStuffValue", "thirdStuffValue"), flash.values());
		flash.endDispatch(event);
		CollectionAssert.containsOnly(Quick.list("newSecondStuffValue", "thirdStuffValue"), flash.values());
	}

	protected void putAfterBeginRequest() {
		flash.put("thirdStuff", "thirdStuffValue");
		flash.put("secondStuff", "newSecondStuffValue");
	}

	@Override
	protected void setUp() throws Exception {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		flash = Flash.load(request, request.getSession());
		event = new ShamEvent(request, new ShamHttpServletResponse());
		flash.put("firstStuff", "firstStuffValue");
		flash.put("secondStuff", "secondStuffValue");
	}
}
