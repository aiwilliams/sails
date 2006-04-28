package org.opensails.functional.tools;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.form.html.ISelectModel;
import org.opensails.sails.form.html.ListSelectModel;
import org.opensails.sails.persist.AbstractIdentifiable;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.browser.TestGetEvent;

public class FormToolTests extends TestCase {
	public void testForm() {
		SailsFunctionalTester t = new SailsFunctionalTester();
		TestGetEvent event = t.createVirtualEvent("mc/ma", "$form.start");
		Page page = t.get(event);
		page.assertContains("method=\"post\"");
		page.assertMatches("action=\"/.*?/mc/ma\"");
	}

	public void testForm_IdGeneration() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();

		StringBuilder template = new StringBuilder();
		template.append("$form.checkbox('the.name')");
		template.append("$form.checkbox('the.name')");
		template.append("$form.radio('the.name', 'thevalue')");
		template.append("$form.checkbox('the.name', 'thevalue')");
		template.append("$form.text('the.name').value('stuff')");

		String[] expected = new String[] { "id=\"the_name\"", "id=\"the_name-1\"", "id=\"the_name-thevalue\"", "id=\"the_name-thevalue-1\"", "id=\"the_name-2\"" };

		Page page = t.getTemplated(template);
		page.assertContainsInOrder(expected);
	}

	@SuppressWarnings("unused")
	public void testForm_SelectModel() {
		SailsFunctionalTester t = new SailsFunctionalTester();
		final MyModel myModel = new MyModel("modelOne");
		t.getApplication().provides(myModel);

		BaseController controller = new BaseController() {
			public MyModel getMySelected() { return myModel; }
			public ISelectModel model() {
				return new ListSelectModel<MyModel>(myModel);
			}
		};

		t.getApplication().registerController("testing", controller);
		Page page = t.getTemplated("testing/index", "$form.select('some.property', $model)");
		page.assertContains("<option value=\"modelOne\">modelOne</option>");

		// Place to show selected bug
		controller.setResult(null);
		t.getApplication().registerController("testing", controller);
		page = t.getTemplated("testing/index", "$form.select('some.property', $model).selected($mySelected)");
		page.assertContains("<option value=\"modelOne\" selected=\"true\">modelOne</option>");
	}

	public static class MyModel extends AbstractIdentifiable {

		private final String name;

		public MyModel(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
