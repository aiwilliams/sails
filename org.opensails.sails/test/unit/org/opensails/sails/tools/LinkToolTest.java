package org.opensails.sails.tools;

import org.opensails.tool.tester.ToolTestCase;

public class LinkToolTest extends ToolTestCase {
	public void testAction() throws Exception {
		assertRender("<a href=\"/shamcontext/shamservlet/tool/myAction\">tool/myAction</a>", "$link.action(myAction)");
		assertRender("<a href=\"/shamcontext/shamservlet/tool/myAction/1/test\">tool/myAction</a>", "$link.action(myAction, [1, 'test'])");

		assertRender("<a href=\"/shamcontext/shamservlet/myController/myAction\">myController/myAction</a>", "$link.action(myController, myAction)");
		assertRender("<a href=\"/shamcontext/shamservlet/myController/myAction/1/test\">myController/myAction</a>", "$link.action(myController, myAction, [1, 'test'])");
	}

	public void testController() throws Exception {
		assertRender("<a href=\"/shamcontext/shamservlet/tool\">tool</a>", "$link.controller()");
		assertRender("<a href=\"/shamcontext/shamservlet/myController\">myController</a>", "$link.controller(myController)");
	}

	public void testHref() throws Exception {
		assertRender("<a href=\"http://myHref\">http://myHref</a>", "$link.href('http://myHref')");
	}

	public void testImage() throws Exception {
		assertRender("<a href=\"/shamcontext/shamservlet/tool/myAction\"><img src=\"http://some/image.jpg\" alt=\"tool/myAction\" /></a>", "$link.action(myAction).image('http://some/image.jpg')");
		assertRender("<a href=\"http://myHref\"><img src=\"http://some/image.jpg\" alt=\"http://myHref\" /></a>", "$link.href('http://myHref').image('http://some/image.jpg')");
	}

	public void testSecure() throws Exception {
		assertRender("<a href=\"https://myHref\">https://myHref</a>", "$link.href('http://myHref').secure");
	}
}
