/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

/**
 * An HTML INPUT of type TEXT.
 */
public class Text extends LabelableInputElement<Text> {
    public static final String TEXT = "text";

    /**
     * @param name
     */
    public Text(String name) {
        super(RENDER_LABEL_BEFORE, Text.TEXT, name);
    }

    /**
     * @param name
     * @param id
     */
    public Text(String name, String id) {
        super(RENDER_LABEL_BEFORE, Text.TEXT, name, id);
    }
}
