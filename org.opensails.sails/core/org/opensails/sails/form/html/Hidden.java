/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

/**
 * An HTML INPUT of type HIDDEN.
 */
public class Hidden extends InputElement<Hidden> {
    public static final String HIDDEN = "hidden";

    public Hidden(String name) {
        super(HIDDEN, name);
    }
}
