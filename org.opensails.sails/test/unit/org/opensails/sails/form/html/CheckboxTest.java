/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class CheckboxTest extends TestCase {
    public void testBoolean() throws Exception {
        Checkbox checkbox = new Checkbox("name");
        checkbox.value("hello, mate");
        checkbox.getBoolean();
        assertEquals("<input name=\"name\" type=\"checkbox\" value=\"hello, mate\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"false\" />", checkbox.toString());
    }

    public void testLabel() throws Exception {
        Checkbox checkbox = new Checkbox("name.besure");
        // setting the label implies that an id is required and desired
        checkbox.label("hello");
        String id = FormElement.idForName("name.besure");
        assertEquals("<input id=\"" + id
                + "\" name=\"name.besure\" type=\"checkbox\" value=\"true\" /><label for=\"" + id + "\">hello</label><input name=\"form.meta.cb.name.besure\" type=\"hidden\" value=\"false\" />", checkbox.toString());
    }

    public void testToString() throws Exception {
        // specifying no value makes this a boolean checkbox
        Checkbox checkbox = new Checkbox("name");
        assertEquals("<input name=\"name\" type=\"checkbox\" value=\"true\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"false\" />", checkbox.toString());

        checkbox = new Checkbox("name");
        checkbox.value("myvalue");
        assertEquals("<input name=\"name\" type=\"checkbox\" value=\"myvalue\" />", checkbox.toString());

        checkbox = new Checkbox("name");
        checkbox.value(false);
        assertEquals("<input name=\"name\" type=\"checkbox\" value=\"false\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"false\" />", checkbox.toString());
    }
}
