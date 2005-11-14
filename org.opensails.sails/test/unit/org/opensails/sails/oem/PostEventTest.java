package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.sails.form.FormFields;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class PostEventTest extends TestCase {
	public void testGetAction_ImageSubmit() {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setPathInfo("controller/actionIsIgnoredWhenFormMetaActionFound");
		request.setParameter(Submit.ACTION_PREFIX + "pressMe.x", "45");
		request.setParameter(Submit.ACTION_PREFIX + "pressMe.y", "23");
		PostEvent event = new PostEvent(SailsApplicationFixture.basic(), request, new ShamHttpServletResponse());
		assertEquals("pressMe", event.getActionName());

		event = new PostEvent(SailsApplicationFixture.basic(), request, new ShamHttpServletResponse());
		request.setParameter(Submit.ACTION_PREFIX + "pressMe_parm1_parm2", "Press Me");
		assertEquals("pressMe", event.getActionName());
	}

	public void testGetActionName() {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setPathInfo("controller/actionIsIgnoredWhenFormMetaActionFound");
		request.setParameter(Submit.ACTION_PREFIX + "pressMe", "Press Me");
		PostEvent event = new PostEvent(SailsApplicationFixture.basic(), request, new ShamHttpServletResponse());
		assertEquals("pressMe", event.getActionName());
	}

	public void testGetParameters() {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setPathInfo("controller/actionIsIgnoredWhenFormMetaActionFound/ignoreParm1/ignoreParm2");
		request.setParameter(Submit.ACTION_PREFIX + "pressMe_parm1_parm2", "Press Me");
		PostEvent event = new PostEvent(SailsApplicationFixture.basic(), request, new ShamHttpServletResponse());
		assertEquals("pressMe", event.getActionName());
		assertEquals("parm1", event.getActionParameters()[0]);
		assertEquals("parm2", event.getActionParameters()[1]);
	}

	public void testGetParameters_ImageSubmit() {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setPathInfo("controller/actionIsIgnoredWhenFormMetaActionFound/ignoreParm1/ignoreParm2");
		request.setParameter(Submit.ACTION_PREFIX + "pressMe.x", "45");
		request.setParameter(Submit.ACTION_PREFIX + "pressMe_parm1_parm2", "Press Me");
		request.setParameter(Submit.ACTION_PREFIX + "pressMe.y", "23");
		PostEvent event = new PostEvent(SailsApplicationFixture.basic(), request, new ShamHttpServletResponse());
		assertEquals("pressMe", event.getActionName());
		assertEquals("parm1", event.getActionParameters()[0]);
		assertEquals("parm2", event.getActionParameters()[1]);
	}

	public void testSetContainer() {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setParameter("paramOne", "paramOneValue");
		PostEvent event = new PostEvent(SailsApplicationFixture.basic(), request, new ShamHttpServletResponse());
		FormFields fields = event.getContainer().instance(FormFields.class);
		assertEquals("paramOneValue", fields.value("paramOne"));
	}
}
