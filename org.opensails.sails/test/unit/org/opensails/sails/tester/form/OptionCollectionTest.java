package org.opensails.sails.tester.form;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class OptionCollectionTest extends TestCase {
	public void testLabels() throws Exception {
		OptionCollection element = new OptionCollection("<select><option value=\"option.one.value\">option one</option><option value=\"option.two.value\">option two</option></select>");
		element.labels("option one", "option two");

		try {
			element.labels("option two", "option one");
			throw new RuntimeException("expection order enforced");
		} catch (AssertionFailedError expected) {
			assertTrue(expected.getMessage().contains("not order"));
		}

		element = new OptionCollection("<select><option value=\"option.one.value\">option one</option></select>");
		try {
			element.labels("option one", "option two");
			throw new RuntimeException("less than expected");
		} catch (AssertionFailedError expected) {
			assertTrue(expected.getMessage().contains("2 labels"));
		}

		element = new OptionCollection("<select><option value=\"option.one.value\">option one</option><option value=\\\"option.two.value\\\">option two</option><option value=\\\"option.three.value\\\">option three</option></select>");
		try {
			element.labels("option one", "option two");
			throw new RuntimeException("more than expected");
		} catch (AssertionFailedError expected) {
			assertTrue(expected.getMessage().contains("2 labels"));
		}
	}

	public void testOptionCollection() {
		// make sure it can be constructed with no options
		new OptionCollection("<select></select>");
	}

	public void testSize() {
		OptionCollection element = new OptionCollection("<select></select>");
		assertEquals(0, element.size());
		element.size(0);
		try {
			element.size(10);
			throw new RuntimeException("failure expected");
		} catch (AssertionFailedError expected) {}

		element = new OptionCollection("<select><option value=\"option.one.value\">option one</option><option value=\"option.two.value\">option two</option></select>");
		assertEquals(2, element.size());
		element.size(2);
	}
}
