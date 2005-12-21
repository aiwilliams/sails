package org.opensails.functional.event;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.EventTestController;
import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionListener;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.form.FormMeta;
import org.opensails.sails.tester.Page;

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
		assertHeardActionEvents();
	}

	public void testGet_NoCodeBehind() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.get("noCodeBehind");
		page.assertContains("noCodeBehind");
	}

	public void testGet_NoImplementation() {
		SailsFunctionalTester tester = new SailsFunctionalTester();
		Page page = tester.get("noImplementation", "index", ArrayUtils.EMPTY_STRING_ARRAY);
		page.assertContains("index");
	}

	public void testGet_Parameters() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		Page page = tester.get("parameterGet", new String[] { "true", "two", "three" });
		page.assertTemplate("eventTest/parameterGet");
		page.assertContains("true");
		page.assertContains("two");
	}

	public void testPost() {
		SailsFunctionalTester tester = new SailsFunctionalTester(EventTestController.class);
		registerAsActionListener(tester);
		Page page = tester.post("simplePost", FormFields.quick("postedField", "postedValue"));
		page.assertTemplate("eventTest/simplePost");
		page.assertContains("postedValue");
		assertHeardActionEvents();
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
}
