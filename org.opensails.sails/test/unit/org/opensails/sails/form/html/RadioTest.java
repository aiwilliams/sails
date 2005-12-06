/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class RadioTest extends TestCase {
    public void testToString() throws Exception {
        Radio radio = new Radio("name", "value");
        assertEquals("<input id=\"name-value\" name=\"name\" type=\"radio\" value=\"value\" />", radio.toString());
        
        radio = new Radio("name", "value", "id");
        assertEquals("<input id=\"id\" name=\"name\" type=\"radio\" value=\"value\" />", radio.toString());
    }
}
