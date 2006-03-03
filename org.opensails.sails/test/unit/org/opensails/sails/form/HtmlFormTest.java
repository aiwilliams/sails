package org.opensails.sails.form;

import junit.framework.TestCase;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.model.oem.SingleModelContext;
import org.opensails.sails.validation.oem.SailsValidationEngine;

public class HtmlFormTest extends TestCase {
    public void testForm() throws Exception {
        ShamFormModel model = new ShamFormModel();

        FormFields formFields = new FormFields();
        HtmlForm form = new HtmlForm(new RequestContainer(new ApplicationContainer()), new SingleModelContext(model), formFields, new AdapterResolver(), new SailsValidationEngine());
        assertTrue(form.isValid());

        form.resetValidation();
        formFields.setValue("stringProperty", "1");
        assertFalse(form.isValid());
    }
}
