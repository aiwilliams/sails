package org.opensails.sails.form;

import junit.framework.*;

import org.opensails.rigging.*;
import org.opensails.sails.*;
import org.opensails.sails.model.oem.*;
import org.opensails.sails.oem.*;
import org.opensails.sails.validation.oem.*;

public class HtmlFormTest extends TestCase {
	public void testForm() throws Exception {
		ShamFormModel model = new ShamFormModel();

		FormFields formFields = new FormFields();
		HtmlForm form = new HtmlForm(new RequestContainer(new ScopedContainer(ApplicationScope.SERVLET)), new SingleModelContext(model), formFields, new AdapterResolver(), new SailsValidationEngine());
		assertTrue(form.isValid());

		form.resetValidation();
		formFields.setValue("stringProperty", "1");
		assertFalse(form.isValid());
	}
}
