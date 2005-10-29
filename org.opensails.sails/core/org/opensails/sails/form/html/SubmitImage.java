package org.opensails.sails.form.html;

import java.io.IOException;

import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;

/**
 * An HTML INPUT of type IMAGE.
 * 
 * @author Adam Williams
 */
public class SubmitImage extends InputElement<SubmitImage> {
    public static final String IMAGE = "image";
    
    protected String srcValue;

    public SubmitImage(String name, String srcValue) {
        super(IMAGE, name);
        this.srcValue = srcValue;
    }
    
    @Override
    protected void writeAttributes(HtmlGenerator generator) throws IOException {
        super.writeAttributes(generator);
        generator.attribute(HtmlConstants.SRC_ATTRIBUTE, srcValue);
    }
}
