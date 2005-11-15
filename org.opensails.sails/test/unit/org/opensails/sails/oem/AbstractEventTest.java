package org.opensails.sails.oem;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.ISailsEventListener;
import org.opensails.sails.Sails;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;
import org.opensails.sails.util.CollectionAssert;

public class AbstractEventTest extends TestCase {
	public void testBeginAndEndDispatch_NotifiesListeners() {
		AbstractEvent event = SailsEventFixture.abstractEvent();
		ShamListener shamListener = new ShamListener();
		event.getContainer().register(shamListener);
		event.beginDispatch();
		event.endDispatch();
		CollectionAssert.containsOnlyOrdered(new String[] { "beginDispatch", "endDispatch" }, shamListener.receivedEvents);
	}

	public void testBeginAndEndDispatch_UsesApplicationContainer() {
		AbstractEvent event = SailsEventFixture.abstractEvent();
		ShamListener shamListener = new ShamListener();
		event.getApplication().getContainer().register(shamListener);
		event.beginDispatch();
		event.endDispatch();
		CollectionAssert.containsOnlyOrdered(new String[] { "beginDispatch", "endDispatch" }, shamListener.receivedEvents);
	}

	public void testResolve_UrlType() throws Exception {
		ISailsApplication application = SailsApplicationFixture.basic();
		AbstractEvent event = SailsEventFixture.abstractEvent(application);
		IUrl url = event.resolve(UrlType.IMAGE, "myImage.jpg");
		assertEquals(event.getRequest().getContextPath() + "/" + application.getConfiguration().getString(Sails.ConfigurationKey.Url.IMAGES) + "/myImage.jpg", url.render());
	}

	public static class ShamListener implements ISailsEventListener {
		List<String> receivedEvents = new ArrayList<String>();

		public void beginDispatch(ISailsEvent event) {
			receivedEvents.add("beginDispatch");
		}

		public void endDispatch(ISailsEvent event) {
			receivedEvents.add("endDispatch");
		}
	}
}
