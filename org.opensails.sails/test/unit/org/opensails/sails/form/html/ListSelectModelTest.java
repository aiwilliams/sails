package org.opensails.sails.form.html;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class ListSelectModelTest extends TestCase {
    protected List list;
    protected ListSelectModel model;

    public void testGetLabel_int() {
        assertEquals("1", model.getLabel(0));
        assertEquals("2", model.getLabel(1));
        assertEquals("3", model.getLabel(2));
    }

    public void testGetLabel_Object() {
        assertEquals("1", model.getLabel(list.get(0)));
        assertEquals("2", model.getLabel(list.get(1)));
        assertEquals("3", model.getLabel(list.get(2)));
    }

    public void testGetOption() {
        assertEquals(list.get(0), model.getOption(0));
        assertEquals(list.get(1), model.getOption(1));
        assertEquals(list.get(2), model.getOption(2));
    }

    public void testGetOptionCount() {
        assertEquals(list.size(), model.getOptionCount());
    }

    public void testGetValue_int() {
        assertEquals("1", model.getValue(0));
        assertEquals("2", model.getValue(1));
        assertEquals("3", model.getValue(2));
    }

    public void testGetValue_Object() {
        assertEquals("1", model.getValue(list.get(0)));
        assertEquals("2", model.getValue(list.get(1)));
        assertEquals("3", model.getValue(list.get(2)));
    }

    public void testTranslateValue() {
        assertEquals(list.get(0), model.translateValue("1"));
        assertEquals(list.get(1), model.translateValue("2"));
        assertEquals(list.get(2), model.translateValue("3"));
        try {
            model.translateValue("");
            fail("Should throw complain when unknown option");
        } catch (IllegalArgumentException e) {}
    }

    @Override
    protected void setUp() {
        list = Arrays.asList(new Object[] { new Integer(1), new Integer(2), new Integer(3) });
        model = new ListSelectModel(list);
    }
}
