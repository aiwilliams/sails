package org.opensails.sails.form.html;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class AlternativeSelectModelTest extends TestCase {
    protected List<Object> list;
    protected AlternativeSelectModel model;
    protected AlternativeSelectModel modelWithAlternative;

    public void testGetLabel_int() {
        assertEquals(AlternativeSelectModel.DEFAULT_ALTERNATIVE_OPTION_LABEL, model.getLabel(0));
        assertEquals("otherLabel", modelWithAlternative.getLabel(0));
        assertEquals("1", model.getLabel(1));
        assertEquals("2", model.getLabel(2));
        assertEquals("3", model.getLabel(3));
    }

    public void testGetLabel_Object() {
        assertEquals(AlternativeSelectModel.DEFAULT_ALTERNATIVE_OPTION_LABEL, model.getLabel(AlternativeSelectModel.DEFAULT_ALTERNATIVE_OPTION_VALUE));
        assertEquals("otherLabel", modelWithAlternative.getLabel("otherValue"));
        assertEquals("1", model.getLabel(list.get(0)));
        assertEquals("2", model.getLabel(list.get(1)));
        assertEquals("3", model.getLabel(list.get(2)));
    }

    public void testGetOption() {
        assertEquals(AlternativeSelectModel.DEFAULT_ALTERNATIVE_OPTION_VALUE, model.getOption(0));
        assertEquals("otherValue", modelWithAlternative.getOption(0));
        assertEquals(list.get(0), model.getOption(1));
        assertEquals(list.get(1), model.getOption(2));
        assertEquals(list.get(2), model.getOption(3));
    }

    public void testGetOptionCount() {
        assertEquals(list.size() + 1, model.getOptionCount());
    }

    public void testGetValue_int() {
        assertEquals(AlternativeSelectModel.DEFAULT_ALTERNATIVE_OPTION_VALUE, model.getValue(0));
        assertEquals("otherValue", modelWithAlternative.getValue(0));
        assertEquals("1", model.getValue(1));
        assertEquals("2", model.getValue(2));
        assertEquals("3", model.getValue(3));
    }

    public void testGetValue_Object() {
        assertEquals(ISelectModel.NULL_OPTION_VALUE, model.getValue(null));
        assertEquals("otherValue", modelWithAlternative.getValue("otherValue"));
        assertEquals("1", model.getValue(list.get(0)));
        assertEquals("2", model.getValue(list.get(1)));
        assertEquals("3", model.getValue(list.get(2)));
    }

    public void testTranslateValue() {
        assertEquals(AlternativeSelectModel.DEFAULT_ALTERNATIVE_OPTION_VALUE, model.translateValue(AlternativeSelectModel.DEFAULT_ALTERNATIVE_OPTION_VALUE));
        assertEquals("otherValue", modelWithAlternative.translateValue("otherValue"));
        assertEquals(list.get(0), model.translateValue("1"));
        assertEquals(list.get(1), model.translateValue("2"));
        assertEquals(list.get(2), model.translateValue("3"));
    }

    @Override
    protected void setUp() {
        list = Arrays.asList(new Object[] { new Integer(1), new Integer(2), new Integer(3) });
        model = new AlternativeSelectModel(list);
        modelWithAlternative = new AlternativeSelectModel(list, "otherLabel", "otherValue");
    }
}
