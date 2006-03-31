package org.opensails.sails.tester.form;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class TextTest extends TestCase {
	public void testLabeled() throws Exception {
		Text text = new Text("<form><label for=\"it\">hehe</label><input name='my.text' type=\"text\" id=\"it\" value=\"das value\" /></form>", "my.text");
		text.assertLabeled("hehe").assertValue("das value");

		text = new Text("<form><label for=\"it\"></label><input name=\'my.text\' type=\"text\" id=\"it\" value=\"das value\" /></form>", "my.text");
		try {
			text.assertLabeled("hehe");
			throw new RuntimeException("label has blank content");
		} catch (AssertionFailedError expected) {}
		try {
			text.assertValue("hehe");
			throw new RuntimeException("value is different");
		} catch (AssertionFailedError expected) {}
	}
}
