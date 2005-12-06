/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class TextTest extends TestCase {
    public void testToString() {
        Text text = new Text("name");
        assertEquals("<input id=\"name\" name=\"name\" type=\"text\" value=\"\" />", text.toString());

        text.value("hellomate");
        assertEquals("<input id=\"name\" name=\"name\" type=\"text\" value=\"hellomate\" />", text.toString());
        
        text = new Text("name", "id");
        assertEquals("<input id=\"id\" name=\"name\" type=\"text\" value=\"\" />", text.toString());
    }
}
