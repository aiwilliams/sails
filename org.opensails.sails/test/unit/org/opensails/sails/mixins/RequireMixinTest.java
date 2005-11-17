package org.opensails.sails.mixins;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.url.UrlType;

public class RequireMixinTest extends TestCase {
	public void testIt() throws Exception {
		ISailsEvent event = SailsEventFixture.sham();
		RequireMixin require = new RequireMixin(event, new ResourceResolver() {
			@Override
			public InputStream resolve(String identifier) {
				assertEquals("component/mycomponent/.component", identifier);
				return new ByteArrayInputStream("one.js\ntwo.js\none.css".getBytes());
			}
		});
		String rootUrl = event.resolve(UrlType.CONTEXT, "component/mycomponent").render();
		String expected = "<script type=\"text/javascript\" src=\"" + rootUrl + "/one.js\"></script><script type=\"text/javascript\" src=\"" + rootUrl
				+ "/two.js\"></script><link href=\"" + rootUrl + "/one.css\" type=\"text/css\" rel=\"stylesheet\" />";
		require.component("mycomponent");
		require.component("mycomponent");
		assertEquals(expected, require.output());
	}

}
