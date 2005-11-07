/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import junit.framework.TestCase;

/**
 * http://www.w3.org/TR/html4/interact/forms.html#h-17.5
 * 
 * A Submit is a input with type="submit". It seems that HTML also has a button
 * element, where type can be submit, but the web seems to talk about the button
 * form of submit being something that can break some browsers. I tested them
 * both on my Mac. The input one looked good. The button version was flat and
 * ugly. So, I am going with input.
 */
public class SubmitTest extends TestCase {
	public void testImage() throws Exception {
		Submit submit = new Submit("name", null);
		submit.value("thevalue");
		submit.action("theaction");

		SubmitImage submitImage = submit.image("srclocation");
		assertEquals("srclocation", submitImage.getSrc());
		// not using getters here cause they do work
		assertEquals(submit.parameters, submitImage.parameters);
		assertEquals(submit.name, submitImage.name);
		assertEquals(submit.value, submitImage.value);
		assertEquals(submit.action, submitImage.action);
	}
}
