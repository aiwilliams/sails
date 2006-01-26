package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class SubmitLinkTest extends TestCase {
    public void testToString() {
        SubmitLink submitLink = new SubmitLink("name", "formName");
        assertEquals("<a id=\"name\" name=\"name\" href=\"javascript:document.forms['formName'].submit()\"></a>", submitLink.toString());

        submitLink.text("hello");
        assertEquals("<a id=\"name\" name=\"name\" href=\"javascript:document.forms['formName'].submit()\">hello</a>", submitLink.toString());
    }

    public void testCtorWithNull() throws Exception {
        try {
            new SubmitLink("name", null);
            fail("You must provide the name of the form for the JavaScript to work");
        } catch (IllegalArgumentException expected) {}
    }

    /**
     * TODO: Allow a link submit to alter the action
     */
    public void testSubmitLink_ActionOtherThanForms() throws Exception {
    // try {
    // form.linkSubmit("Link text", "customAction");
    // fail("If the Form id has not been defined, we can't create a link submit
    // that is bound to the correct form.");
    // } catch (IllegalStateException e) {
    // }
    // form.id("myForm");
    // String expected = "<script type='text/javascript'><!-- var myForm =
    // document.forms.myForm; ";
    // assertEquals("<a href=\"javascript:document.forms.myForm.submit\">Link
    // text</a>", form.linkSubmit("Link text", "customAction"));
    }
}
