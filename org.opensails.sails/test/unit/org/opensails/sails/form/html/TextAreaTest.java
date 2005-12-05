/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class TextAreaTest extends TestCase {
    public void testToString() throws Exception {
        TextArea textArea = new TextArea("name");
        assertEquals("<textarea name=\"name\"></textarea>", textArea.toString());
        
        textArea.value("heloo");
        assertEquals("<textarea name=\"name\">heloo</textarea>", textArea.toString());

        textArea = new TextArea("name").id("id");
        assertEquals("<textarea id=\"id\" name=\"name\"></textarea>", textArea.toString());
    }
}
