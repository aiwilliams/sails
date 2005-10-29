/*
 * Created on May 17, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

/**
 * An HTML INPUT of type RADIO.
 */
public class Radio extends LabelableInputElement<Radio> {
    public static final String RADIO = "radio";

    /**
     * @param name
     * @param value required for a radio
     */
    public Radio(String name, String value) {
        super(RENDER_LABEL_AFTER, RADIO, name);
        value(value);
    }

    /**
     * @param name
     * @param value required for a radio
     * @param id
     */
    public Radio(String name, String value, String id) {
        super(RENDER_LABEL_AFTER, RADIO, name, id);
        value(value);
    }
}
