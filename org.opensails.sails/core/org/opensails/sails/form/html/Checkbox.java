/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import java.io.IOException;
import java.io.Writer;

import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.html.HtmlConstants;

/**
 * An HTML INPUT of type CHECKBOX.
 */
public class Checkbox extends LabelableInputElement<Checkbox> {
    public static final String CHECKBOX = "checkbox";
    public static final String FORM_META = HtmlForm.META_PREFIX + "cb.";

    protected Hidden hiddenForBoolean;

    /**
     * @param name
     */
    public Checkbox(String name) {
        super(RENDER_LABEL_AFTER, CHECKBOX, name);
        value = Boolean.TRUE.toString();
        getBoolean();
    }

    /**
     * @param name
     */
    public Checkbox(String name, String value) {
        super(RENDER_LABEL_AFTER, CHECKBOX, name);
        this.value = value;
    }

    public Checkbox checked(boolean b) {
        value = Boolean.toString(b);
        return this;
    }

    /**
     * If called, the Checkbox is rendered with a Hidden. This technology allows
     * for non-JavaScript maintenance of the checked state of a boolean property
     * on an object. It is named getBoolean to allow templates to call it like
     * this:
     * 
     * checkbox.boolean.checked(true)
     */
    public Checkbox getBoolean() {
        if (hiddenForBoolean == null) {
            hiddenForBoolean = new Hidden(FORM_META + name);
            hiddenForBoolean.value(HtmlConstants.FALSE);
        }
        return this;
    }

    /**
     * @param writer
     * @throws IOException
     */
    @Override
    public void toString(Writer writer) throws IOException {
        super.toString(writer);
        if (hiddenForBoolean != null || HtmlConstants.TRUE.equals(value) || HtmlConstants.FALSE.equals(value)) {
            getBoolean();
            hiddenForBoolean.toString(writer);
        }
    }

    @Override
    public Checkbox value(String value) {
        // get rid of it if the values don't match
        if (hiddenForBoolean != null && (hiddenForBoolean.value != null && !hiddenForBoolean.value.equals(value))) hiddenForBoolean = null;
        return super.value(value);
    }
}
