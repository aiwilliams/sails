package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.sails.form.FormFields;
import org.opensails.sails.servletapi.ShamHttpServletRequest;
import org.opensails.sails.servletapi.ShamHttpServletResponse;

public class PostEventTest extends TestCase {
	public void testSetContainer() {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setParameter("paramOne", "paramOneValue");
		PostEvent event = new PostEvent(SailsApplicationFixture.basic(), request, new ShamHttpServletResponse());
		SailsEventFixture.setContainer(event);
		FormFields fields = event.getContainer().instance(FormFields.class);
		assertEquals("paramOneValue", fields.value("paramOne"));
	}
}
