package org.opensails.sails.url;

import java.util.List;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.util.Quick;

public class ActionUrlTest extends TestCase {
	SailsFunctionalTester t = new SailsFunctionalTester();
	ActionUrl url = t.actionUrl("myAction");

	public void testAppendParameter() {
		url.setParameters("one", "two");
		url.appendParameter("three");
		assertEquals("/one/two/three", url.getParametersString());
	}

	public void testDoRender() {
		assertEquals("/shamcontext/shamservlet/home/myAction", url.doRender());

		url.appendParameter("my Param");
		url.setQueryParameter("myQP", "qp v");
		assertEquals("/shamcontext/shamservlet/home/myAction/my+Param?myQP=qp+v", url.doRender());
	}

	public void testRenderAbsoluteUrl() throws Exception {
		assertEquals("http://localhost/shamcontext/shamservlet/home/myAction", url.renderAbsoluteUrl());

		url.appendParameter("my Param");
		url.setQueryParameter("myQP", "qp v");
		assertEquals("http://localhost/shamcontext/shamservlet/home/myAction/my+Param?myQP=qp+v", url.renderAbsoluteUrl());
	}

	public void testRenderThyself() {
		assertEquals("/shamcontext/shamservlet/home/myAction", url.renderThyself());

		url.appendParameter("my Param");
		url.setQueryParameter("myQP", "qp v");
		assertEquals("/shamcontext/shamservlet/home/myAction/my+Param?myQP=qp+v", url.renderThyself());
	}

	public void testRenderThyself_EncodeSession() {
		t.disableCookies();
		assertEquals("/shamcontext/shamservlet/home/myAction;sessionencoded=ShamSession", url.renderThyself());
	}

	public void testSetAction() throws Exception {
		url.setAction("newAction");
		assertEquals("/shamcontext/shamservlet/home/newAction", url.renderThyself());

		url.setAction(null);
		assertEquals("/shamcontext/shamservlet/home", url.renderThyself());
	}

	public void testSetController() throws Exception {
		url.setController("newController");
		assertEquals("/shamcontext/shamservlet/newController/myAction", url.renderThyself());

		url.setController(null);
		assertEquals("Shouldn't happen, but if it does, just use the controller of the event", "/shamcontext/shamservlet/home/myAction", url.renderThyself());
	}

	public void testSetParameters_List() {
		url.setParameters(Quick.<Object> list(1, "test"));
		assertEquals("/1/test", url.getParametersString());

		url.setParameters((List) null);
		assertEquals("", url.getParametersString());
	}

	public void testSetParameters_ObjectArray() {
		url.setParameters(1, "test");
		assertEquals("/1/test", url.getParametersString());

		url.setParameters((Object[]) null);
		assertEquals("", url.getParametersString());
	}
}
