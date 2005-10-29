package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class FormElementTest extends TestCase {
    public void testIdForName() throws Exception {
        assertEquals("some_name", FormElement.idForName("some.name"));
        assertEquals("somename", FormElement.idForName("somename"));
    }

}
