package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class FormElementTest extends TestCase {
    public void testIdForName() throws Exception {
        assertEquals("some_name", FormElement.idForName("some.name"));
        assertEquals("somename", FormElement.idForName("somename"));
    }

    public void testIdForNameAndValue() throws Exception {
    	assertEquals("some_name-value", FormElement.idForNameAndValue("some.name", "value"));
    	assertEquals("some_name", FormElement.idForNameAndValue("some.name", ""));
    	assertEquals("value", FormElement.idForNameAndValue("", "value"));
    }
}
