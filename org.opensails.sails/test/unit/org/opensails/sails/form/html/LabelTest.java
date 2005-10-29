/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class LabelTest extends TestCase {
    public void testToString() throws Exception {
        Label label = new Label(new Text("myname", "myid"));
        assertEquals("<label for=\"myid\"></label>", label.toString());
        
        label.text("Hello");
        assertEquals("<label for=\"myid\">Hello</label>", label.toString());
    }
}
