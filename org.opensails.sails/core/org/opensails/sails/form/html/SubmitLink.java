package org.opensails.sails.form.html;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;

public class SubmitLink extends FormElement<SubmitLink> {
    public static final String ANCHOR = "a";

    /**
     * Rendered in the body of the anchor.
     */
    protected String body;

    /**
     * Used to reference form by name within document.
     */
    protected String formId;

    public SubmitLink(String name, String formId) {
        super(ANCHOR, name);
        if (StringUtils.isBlank(formId))
            throw new IllegalArgumentException("You must provide the id of the form for a submit link to work");
        this.formId = formId;
    }

    public SubmitLink text(String text) {
        this.body = text;
        return this;
    }

    @Override
    protected void body(HtmlGenerator generator) throws IOException {
        if (body != null) generator.write(body);
    }

    @Override
    protected boolean hasBody() {
        return true;
    }

    @Override
    protected void writeAttributes(HtmlGenerator generator) throws IOException {
        super.writeAttributes(generator);
        generator.attribute(HtmlConstants.HREF_ATTRIBUTE, "javascript:document.forms['" + formId + "'].submit()");
    }
}
