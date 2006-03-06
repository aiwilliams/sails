package org.opensails.functional.event;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.EventTestController;
import org.opensails.functional.controllers.EventTestSubclassController;
import org.opensails.functional.controllers.ExampleEnum;
import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionListener;
import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.form.FormMeta;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.browser.ShamFormFields;

public class EventProcessingTests extends TestCase implements IActionListener {
	private int beginExecutionCallCount = 0;

	private int endExecutionCallCount;

	public void beginExecution(IAction action) {
		beginExecutionCallCount++;
	}

	public void endExecution(IAction action) {
		endExecutionCallCount++;
	}

	public void testActionReturnsResult() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.get("actionReturnsResult");
		page.assertContains("string rendered");
	}

	public void testGet() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		registerAsActionListener(tester);
		Page page = tester.get("simpleGet");
		page.assertTemplate("eventTest/simpleGet");
		page.assertContentType(ContentType.TEXT_HTML);
		assertHeardActionEvents();
	}

	public void testGet_ActionsInSuperclass() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestSubclassController.class);
		registerAsActionListener(tester);
		Page page = tester.get("simpleGet");
		page.assertTemplate("eventTestSubclass/simpleGet");
		assertHeardActionEvents();
	}

	public void testGet_DifferentTemplateRendered() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.get("differentTemplate");
		page.assertContains("rendered different/template");
	}

	public void testGet_NoCodeBehind() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.get("noCodeBehind");
		page.assertContains("noCodeBehind");
	}

	public void testGet_NoImplementation() {
		SailsFunctionalTester tester = new SailsFunctionalTester();
		Page page = tester.get("noImplementation", "index", ArrayUtils.EMPTY_OBJECT_ARRAY);
		page.assertContains("index");
	}

	public void testGet_Parameters() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.get("parameterGet", new Object[] { "true", "two", ExampleEnum.ENUM_EXAMPLE_TWO, "four" });
		page.assertTemplate("eventTest/parameterGet");
		page.assertContains("true");
		page.assertContains("two");
		page.assertContains(ExampleEnum.ENUM_EXAMPLE_TWO.name());

		page = tester.get("parameterGet");
		page.assertRenderFails("Arguments weren't exposed: zero parameter event for parameterized action, code doesn't get executed");
	}

	public void testPost() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		registerAsActionListener(tester);
		Page page = tester.post("simplePost", FormFields.quick("postedField", "postedValue"));
		page.assertTemplate("eventTest/simplePost");
		page.assertContains("postedValue");
		assertHeardActionEvents();
	}

	public void testPost_FieldsAdaptedAndSet() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		tester.registerAdapter(MyDomainModel.class, MyAdapter.class);

		ShamFormFields formFields = tester.getFormFields();
		formFields.setValue("stringField", "postedStringFieldValue");
		formFields.setValue("intField", 3);
		formFields.setValue("floatField", 5.4);
		formFields.setValue("enumField", ExampleEnum.ENUM_EXAMPLE_ONE);
		formFields.setValues("stringArrayField", "one", "two");
		formFields.setValues("objectArrayField", "hello", "there");

		Page page = tester.post("simplePost", formFields);
		page.assertContains("postedStringFieldValue");
		page.assertContains("3");
		page.assertContains(ExampleEnum.ENUM_EXAMPLE_ONE.name());
		page.assertContains("[one, two]");
		page.assertContains("[hello, there]");
		page.assertExcludes("5.4");
	}

	public void testPost_MetaAction_ImageSubmit_Parameters() {
		FormFields fields = new FormFields();
		fields.setValue("postedField", "postedValue");
		fields.setValue(FormMeta.action("parameterPost", "one", "2"), "Submit Label");
		fields.setValue(FormMeta.action("parameterPost.x"), "45");
		fields.setValue(FormMeta.action("parameterPost.y"), "23");

		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.post("notTheActionInTheMeta", FormFields.quick("postedField", "postedValue", FormMeta.action("parameterPost", "one", "2"), "Submit Label"), "three", "four", "areIgnored");
		page.assertContains("postedValue");
		page.assertContains("one");
		page.assertContains("2");
	}

	public void testPost_MetaAction_Parameters() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.post("notTheActionInTheMeta", FormFields.quick("postedField", "postedValue", FormMeta.action("parameterPost", "one", "2"), "Submit Label"), "three", "four", "areIgnored");
		page.assertContains("postedValue");
		page.assertContains("one");
		page.assertContains("2");
	}

	public void testPost_Parameters() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.post("parameterPost", FormFields.quick("postedField", "postedValue"), "one", "2");
		page.assertContains("postedValue");
		page.assertContains("one");
		page.assertContains("2");
	}

	private void assertHeardActionEvents() {
		// Once for application container, once for event container
		assertEquals(2, beginExecutionCallCount);
		assertEquals(2, endExecutionCallCount);
	}

	private void registerAsActionListener(SailsFunctionalTester tester) {
		tester.getContainer().register(this);
		tester.getRequestContainer().register(this);
	}

	public static class MyAdapter extends AbstractAdapter<MyDomainModel, String> {
		public MyDomainModel forModel(Class<? extends MyDomainModel> modelType, String fromWeb) throws AdaptationException {
			return new MyDomainModel(fromWeb);
		}

		public String forWeb(Class<? extends MyDomainModel> modelType, MyDomainModel fromModel) throws AdaptationException {
			return fromModel.web;
		}
	}

	public static class MyDomainModel {
		public final String web;

		public MyDomainModel(String fromWeb) {
			this.web = fromWeb;
		}
	}
}
