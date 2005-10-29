/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class SubmitTest extends TestCase {
    public void testToString() {
        Submit submit = new Submit("name");
        assertEquals("<input name=\"name\" type=\"submit\" value=\"\" />", submit.toString());
        
        submit.value("hehe");
        assertEquals("<input name=\"name\" type=\"submit\" value=\"hehe\" />", submit.toString());
    }
    
    public void testImage() throws Exception {
        Submit submit = new Submit("name");
        SubmitImage submitImage = submit.image("srclocation");
        assertEquals("name", submitImage.getName());
    }
}
