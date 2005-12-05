/*
 * Created on May 14, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class SelectTest extends TestCase {
    public void testToString() throws Exception {
        Select select = new Select("name");
        assertEquals("<select name=\"name\" disabled=\"true\"></select>", select.toString());

        select = new Select("name").id("id");
        assertEquals("<select id=\"id\" name=\"name\" disabled=\"true\"></select>", select.toString());
    }

    public void testToString_Attributes() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("myattr", "myvalue");
        attributes.put("myattrnull", null);
        attributes.put("myattrempty", " ");
        
        SelectModel selectModel = new ListSelectModel(new Object[] { "one", "two" });
        Select select = new Select("name", selectModel, attributes).id("id");
        String expected = "<select id=\"id\" name=\"name\" myattr=\"myvalue\" myattrempty=\" \"><option value=\"" + SelectModel.NULL_OPTION_VALUE + "\" selected=\"true\"></option><option value=\"one\">one</option><option value=\"two\">two</option></select>";
        assertEquals(expected, select.toString());
    }
    
    public void testToString_SelectModel() {
        SelectModel selectModel = new ListSelectModel(new Object[] { "one", "two" });
        Select select = new Select("name", selectModel).id("id");
        String expected = "<select id=\"id\" name=\"name\"><option value=\"" + SelectModel.NULL_OPTION_VALUE + "\" selected=\"true\"></option><option value=\"one\">one</option><option value=\"two\">two</option></select>";
        assertEquals(expected, select.toString());
        
        select.selected("one");
        expected = "<select id=\"id\" name=\"name\"><option value=\"one\" selected=\"true\">one</option><option value=\"two\">two</option></select>";
        assertEquals(expected, select.toString());
    }
}
