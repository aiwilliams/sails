package org.opensails.sails.oem;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.util.CollectionAssert;

public class FlashTest extends TestCase {
	protected Flash flash = new Flash();

	public void testContainsKey() throws Exception {
		assertTrue(flash.containsKey("firstStuff"));
		flash.beginDispatch(null);
		assertTrue(flash.containsKey("firstStuff"));
		flash.put("more", "stuff");
		assertTrue(flash.containsKey("more"));
		flash.endDispatch(null);
		assertFalse(flash.containsKey("firstStuff"));
		assertTrue(flash.containsKey("more"));
	}

	public void testContainsValue() throws Exception {
		assertTrue(flash.containsValue("firstStuffValue"));
		flash.beginDispatch(null);
		assertTrue(flash.containsValue("firstStuffValue"));
		flash.put("more", "stuff");
		assertTrue(flash.containsValue("stuff"));
		flash.endDispatch(null);
		assertFalse(flash.containsValue("firstStuffValue"));
		assertTrue(flash.containsValue("stuff"));
	}

	public void testGet() {
		assertEquals("firstStuffValue", flash.get("firstStuff"));
		assertEquals("Second access, value should still exist", "firstStuffValue", flash.get("firstStuff"));

		flash.beginDispatch(null);
		assertEquals("We have marked the beginning of a request cycle - values still availble", "firstStuffValue", flash.get("firstStuff"));

		putAfterBeginRequest();
		assertEquals("thirdStuffValue", flash.get("thirdStuff"));
		assertEquals("newSecondStuffValue", flash.get("secondStuff"));

		flash.endDispatch(null);
		assertNull("The old stuff should be gone", flash.get("firstStuff"));
		assertEquals("The new stuff should still be available", "thirdStuffValue", flash.get("thirdStuff"));
		assertEquals("A value was added with same key as expired value - make sure it's kept", "newSecondStuffValue", flash.get("secondStuff"));
	}

	public void testIsEmpty() throws Exception {
		assertFalse(flash.isEmpty());
		flash.beginDispatch(null);
		assertFalse(flash.isEmpty());
		flash.put("new", "value");
		flash.endDispatch(null);
		assertFalse(flash.isEmpty());

		flash.beginDispatch(null);
		flash.endDispatch(null);
		assertTrue(flash.isEmpty());
	}

	public void testKeySet() throws Exception {
		CollectionAssert.containsOnly(new String[] { "firstStuff", "secondStuff" }, flash.keySet());
		flash.beginDispatch(null);
		CollectionAssert.containsOnly(new String[] { "firstStuff", "secondStuff" }, flash.keySet());
		putAfterBeginRequest();
		CollectionAssert.containsOnly(new String[] { "firstStuff", "secondStuff", "thirdStuff" }, flash.keySet());
		flash.endDispatch(null);
		CollectionAssert.containsOnly(new String[] { "secondStuff", "thirdStuff" }, flash.keySet());
	}

	public void testPutAll() throws Exception {
		Map<Object, Object> more = new HashMap<Object, Object>();
		more.put("moreOne", "moreOneValue");
		more.put("moreTwo", "moreTwoValue");
		flash.putAll(more);
		assertEquals("moreOneValue", flash.get("moreOne"));
		assertEquals("moreTwoValue", flash.get("moreTwo"));

		flash.beginDispatch(null);
		flash.put("moreTwo", "newMoreTwoValue");
		assertEquals("newMoreTwoValue", flash.get("moreTwo"));

		flash.endDispatch(null);
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
		flash.beginDispatch(null);
		assertEquals(2, flash.size());
		putAfterBeginRequest();
		assertEquals(3, flash.size());
		flash.endDispatch(null);
		assertEquals(2, flash.size());
	}

	public void testValues() {
		CollectionAssert.containsOnly(new String[] { "firstStuffValue", "secondStuffValue" }, flash.values());
		flash.beginDispatch(null);
		CollectionAssert.containsOnly(new String[] { "firstStuffValue", "secondStuffValue" }, flash.values());
		putAfterBeginRequest();
		CollectionAssert.containsOnly(new String[] { "firstStuffValue", "newSecondStuffValue", "thirdStuffValue" }, flash.values());
		flash.endDispatch(null);
		CollectionAssert.containsOnly(new String[] { "newSecondStuffValue", "thirdStuffValue" }, flash.values());
	}

	protected void putAfterBeginRequest() {
		flash.put("thirdStuff", "thirdStuffValue");
		flash.put("secondStuff", "newSecondStuffValue");
	}

	@Override
	protected void setUp() throws Exception {
		flash.put("firstStuff", "firstStuffValue");
		flash.put("secondStuff", "secondStuffValue");
	}
}
