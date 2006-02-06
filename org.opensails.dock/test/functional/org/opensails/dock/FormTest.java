package org.opensails.dock;

import junit.framework.TestCase;

import org.opensails.dock.controllers.FormController;
import org.opensails.dock.model.User;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.SailsTester;
import org.opensails.sails.tester.form.Form;
import org.opensails.sails.tester.form.TestFormFields;

public class FormTest extends TestCase {
    public void testBasicFormRendering() throws Exception {
        SailsTester tester = new DockTester();
        tester.setWorkingContext(FormController.class);
        Page page = tester.get("basic");
        Form form = page.form();
        form.submit("Push Me");
        form.text("example.text").labeled("Example Text");
        form.select("example.select").labeled("Example Select").options().labels("", "option one", "option two");
        form.checkbox("example.checkbox").labeled("Example Checkbox").checked(false);
        form.checkbox("example.checkbox.multiple").value("one").checked(true);
        form.checkbox("example.checkbox.multiple").value("two").checked(false);
        form.radio("example.radio").labeled("Example Radio").checked(false);
        form.textarea("example.textarea").labeled("Example Textarea");
    }

    public void testFormProcessing() throws Exception {
        SailsTester tester = new DockTester();

        User user = new User();
        FormFields formFields = new FormFields();
        formFields.setValue("user.firstName", "James");
        Page page = tester.post(FormController.class, "basicPost", formFields, user);
        page.form().validated();
        assertEquals("James", user.getFirstName());
    }

    public void testMultiButton() throws Exception {
        SailsTester tester = new DockTester();

        TestFormFields formFields = tester.getFormFields();
        formFields.submit("actionOne");
        Page page = tester.post(FormController.class, formFields);
        page.assertContains("actionOne() invoked");
    }
}
