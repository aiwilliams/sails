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
 * 
 * One of the thoughts that I had after looking at the way ASP.NET works was
 * having the FormHelper output a hidden field at the bottom of the form that
 * included information that could be used on the server side to determine which
 * action to execute. After some consideration, I decided that, for now, it is
 * preferable to not have a dependency on the form helper. Therefore, the name
 * is utilized.
 * 
 * If the form author does not declare the action on the Submit (using
 * #action(String)), then they must make the form action attribute point to the
 * action.
 */
public class SubmitTest extends TestCase {
	public void testToString() {
		Submit submit = new Submit("name");
		assertEquals("<input name=\"name\" type=\"submit\" value=\"\" />", submit.toString());

		submit.value("hehe");
		assertEquals("<input name=\"name\" type=\"submit\" value=\"hehe\" />", submit.toString());

		/*
		 * Here we drop the name that was given and embed the action to execute.
		 * If they want multiple buttons, they must tell us which action this
		 * Submit is bound to.
		 */
		submit.action("myAction");
		assertEquals("<input name=\"" + Submit.ACTION_PREFIX + "myAction\" type=\"submit\" value=\"hehe\" />", submit.toString());
	}

	public void testImage() throws Exception {
		Submit submit = new Submit("name");
		SubmitImage submitImage = submit.image("srclocation");
		assertEquals("name", submitImage.getName());
	}
}
