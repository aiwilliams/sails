package org.opensails.sails.form;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class FormFieldsTest extends TestCase {
    public void testGet_EachOfThem() throws Exception {
        String string = "value";
        String[] array = new String[] { string };

        Map<String, Object> back = new HashMap<String, Object>();
        back.put("string", string);
        back.put("stringArray", array);

        FormFields map = new FormFields(back);
        assertEquals(string, map.actualValue("string"));
        assertEquals(array, map.actualValue("stringArray"));
        assertEquals(string, map.value("string"));
        assertEquals(string, map.value("stringArray"));
        assertTrue(Arrays.equals(array, map.values("string")));
        assertEquals(array, map.values("stringArray"));
    }
    
    public void testSet_EachOfThem() throws Exception {
        FormFields map = new FormFields();
        map.setValue("something", "else");
        assertEquals("else", map.value("something"));
    }
}
