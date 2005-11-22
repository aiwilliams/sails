package org.opensails.sails.tester.form;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class TextTest extends TestCase {
	public void testLabeled() throws Exception {
		Text text = new Text("<form><label for=\"it\">hehe</label><input name='my.text' type=\"text\" id=\"it\" value=\"das value\" /></form>", "my.text");
		text.labeled("hehe");

		text = new Text("<form><label for=\"it\"></label><input name=\'my.text\' type=\"text\" id=\"it\" value=\"das value\" /></form>", "my.text");
		try {
			text.labeled("hehe");
			throw new RuntimeException("label has blank content");
		} catch (AssertionFailedError expected) {}
	}
}
