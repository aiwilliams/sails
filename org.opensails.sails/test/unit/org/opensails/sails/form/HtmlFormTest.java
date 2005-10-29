package org.opensails.sails.form;

import junit.framework.TestCase;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.model.oem.SingleModelContext;
import org.opensails.sails.oem.AdapterResolver;
import org.opensails.sails.validation.oem.SailsValidationEngine;

public class HtmlFormTest extends TestCase {
    public void testForm() throws Exception {
        ShamFormModel model = new ShamFormModel();

        FormFields formFields = new FormFields();
        HtmlForm form = new HtmlForm(new ScopedContainer(ApplicationScope.REQUEST), new SingleModelContext(model), formFields, new AdapterResolver(), new SailsValidationEngine());
        assertTrue(form.isValid());

        form.resetValidation();
        formFields.setValue("stringProperty", "1");
        assertFalse(form.isValid());
    }
}
