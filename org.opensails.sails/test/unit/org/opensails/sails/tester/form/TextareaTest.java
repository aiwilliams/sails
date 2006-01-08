package org.opensails.sails.tester.form;

import junit.framework.TestCase;

public class TextareaTest extends TestCase {
	public void testIt() throws Exception {
		Textarea text = new Textarea("<form><label for=\"it\">hehe</label><textarea name='my.textarea' id=\"it\">das value</textarea></form>", "my.textarea");
		text.labeled("hehe").value("das value");
	}
}