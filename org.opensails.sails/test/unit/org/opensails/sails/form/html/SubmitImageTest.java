package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class SubmitImageTest extends TestCase {
	public void testToString() {
		SubmitImage imageSubmit = new SubmitImage("name", "src", null);
		assertEquals("<input id=\"name\" name=\"name\" type=\"image\" value=\"\" src=\"src\" />", imageSubmit.toString());
	}
}
