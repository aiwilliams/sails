package org.opensails.functional.component;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.opensails.component.tester.SailsComponentTester;
import org.opensails.component.tester.TestComponent;
import org.opensails.functional.FunctionalTestConfigurator;
import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.components.BasicComponent;
import org.opensails.functional.components.ScriptInitComponent;
import org.opensails.sails.component.ComponentInitializationException;
import org.opensails.sails.template.Require;
import org.opensails.sails.tester.Page;

public class BasicComponentTests extends TestCase {
	public void testRequires() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("componentTest", "requires", ArrayUtils.EMPTY_STRING_ARRAY);
		page.assertMatches(expectedAssetRequirements());
	}

	public void testBasic() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("componentTest", "basic", ArrayUtils.EMPTY_STRING_ARRAY);
		page.assertRenders();
		page.assertContains("both from controller");
		page.assertContains("hello from controller");
		page.assertContains("both from component");
		page.assertContains("hello from component");

		page = t.get(BasicComponent.class, "someCallback");
		page.assertContains("yes, callbackMade");
	}

	public void testNew_WrongArguments() throws Exception {
		SailsComponentTester t = new SailsComponentTester(FunctionalTestConfigurator.class);
		TestComponent<BasicComponent> c = t.component(BasicComponent.class);
		try {
			c.render();
			fail("Should complain");
		} catch (ComponentInitializationException expected) {}
	}

	public void testNullPassedToInitialize() throws Exception {
		SailsComponentTester t = new SailsComponentTester(FunctionalTestConfigurator.class);
		TestComponent<BasicComponent> c = t.component(BasicComponent.class);
		c.initialize(new Object[] { null });
	}
	
	public void testScriptInit() throws Exception {
		SailsComponentTester t = new SailsComponentTester(FunctionalTestConfigurator.class);
		TestComponent<ScriptInitComponent> c = t.component(ScriptInitComponent.class);
		ScriptInitComponent component = c.initialize("someid");
		component.property = "asdf";
		component.classProperty = SailsComponentTester.class;
		assertEquals("someid_something", component.idfor("something"));
		Page page = c.render();
		assertEquals("window.someid = new ScriptInit({property: 'asdf', classProperty: 'org.opensails.component.tester.SailsComponentTester', id: 'someid', callback: Component.callback('callback', 'http://localhost/shamcontext/shamservlet/component_scriptInit/callback', {method: 'get', parameters: 'classProperty=org.opensails.component.tester.SailsComponentTester&id=someid'}), something: $('someid_something')});", page.source());
		assertTrue(c.getRequestContainer().instance(Require.class).output().componentApplicationScripts().contains("common/scripts/component.js"));
		assertTrue(c.getRequestContainer().instance(Require.class).output().componentApplicationScripts().contains("common/scripts/prototype.js"));
	}

	/**
	 * The required scripts come before the implicit scripts as the implicit
	 * scripts use the required scripts. The required styles come before the
	 * implicit styles.
	 */
	String expectedAssetRequirements() {
		List<String> orderedExpectations = new ArrayList<String>();
		orderedExpectations.add("shamcontext/scripts/componentConfigRequiredAppScope.js");
		orderedExpectations.add("components/widget/scripts/componentConfigRequired.js");
		orderedExpectations.add("components/widget/script.js");
		orderedExpectations.add("controllerViewRequiredBeforeComponent.js");
		orderedExpectations.add("controllerViewRequiredAfterComponent.js");
		orderedExpectations.add("shamcontext/styles/componentConfigRequiredAppScope.css");
		orderedExpectations.add("components/widget/componentConfigRequired.css");
		orderedExpectations.add("components/widget/style.css");
		return StringUtils.join(orderedExpectations.toArray(), ".*?");
	}
}
