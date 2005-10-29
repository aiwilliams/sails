/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;

/**
 * An HTML INPUT.
 */
public class InputElement<T extends InputElement> extends ValueElement<T> {
    public static final String INPUT = "input";

    /**
     * The INPUT type attribute value.
     */
    protected String typeValue;

    /**
     * @param elementName
     * @param name
     */
    public InputElement(String typeValue, String name) {
        super(InputElement.INPUT, name);
        this.typeValue = typeValue;
    }

    /**
     * @param typeValue
     * @param name
     * @param id
     */
    public InputElement(String typeValue, String name, String id) {
        this(typeValue, name);
        this.id = id;
    }

    @Override
    protected void writeAttributes(HtmlGenerator generator) throws IOException {
        super.writeAttributes(generator);
        generator.attribute(HtmlConstants.TYPE_ATTRIBUTE, typeValue).valueAttribute(value == null ? StringUtils.EMPTY : value);
    }
}
