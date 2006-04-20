package org.opensails.sails.tools;

import org.opensails.tool.tester.ToolTestCase;

public class UrlforToolTests extends ToolTestCase {
	public void testAction() {
		assertRenderMatches("mycontroller/someaction", "http://.*?/mycontroller/myaction;sessionencoded", "$urlfor.action(myaction)");
	}

	public void testAction_WithController() {
		assertRenderMatches("mycontroller/myaction", "http://.*?/anothercontroller/anotheraction;sessionencoded", "$urlfor.action(anothercontroller, anotheraction)");
	}

	public void testAction_WithParameters() {
		assertRenderMatches("mycontroller/someaction", "http://.*?/mycontroller/myaction/paramOne/paramTwo;sessionencoded", "$urlfor.action(myaction, [paramOne, paramTwo])");
	}

	public void testController() throws Exception {
		assertRenderMatches("mycontroller/someaction", "http://.*?/mycontroller;sessionencoded", "$urlfor.controller");
	}

	public void testController_Specified() {
		assertRenderMatches("mycontroller/someaction", "http://.*?/yourcontroller;sessionencoded", "$urlfor.controller(yourcontroller)");
	}

	public void testImage() {
		assertRenderMatches("mycontroller/someaction", "/.*?/.*?/my.jpg$", "$urlfor.image('my.jpg')");
	}

	public void testScript() {
		assertRenderMatches("mycontroller/someaction", "/.*?/.*?/my.js$", "$urlfor.script('my.js')");
	}

	public void testStyle() {
		assertRenderMatches("mycontroller/someaction", "/.*?/.*?/my.css$", "$urlfor.style('my.css')");
	}

	protected void setUp() {
		t.createSession();
		t.disableCookies();
	}
}
