package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.Sails;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class AbstractEventTest extends TestCase {
	public void testResolve_UrlType() throws Exception {
		ISailsApplication application = SailsApplicationFixture.basic();
		AbstractEvent event = SailsEventFixture.abstractEvent(application);
		IUrl url = event.resolve(UrlType.IMAGE, "myImage.jpg");
		assertEquals(event.getRequest().getContextPath() + "/" + application.getConfiguration().getString(Sails.ConfigurationKey.Url.IMAGES) + "/myImage.jpg", url.render());
	}
}
