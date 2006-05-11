package org.opensails.sails.tester.form;


public class TesterOptionListTest extends TesterElementTestCase {
	public void testLabels() throws Exception {
		TesterOptionList element = new TesterOptionList(elementForHtml("<select><option value=\"option.one.value\">option one</option><option value=\"option.two.value\">option two</option></select>"));
		element.assertLabels("option one", "option two");

		try {
			element.assertLabels("option two", "option one");
			throw new RuntimeException("expection order enforced");
		} catch (TesterElementError expected) {
			assertTrue(expected.getMessage().contains("not order"));
		}

		element = new TesterOptionList(elementForHtml("<select><option value=\"option.one.value\">option one</option></select>"));
		try {
			element.assertLabels("option one", "option two");
			throw new RuntimeException("less than expected");
		} catch (TesterElementError expected) {
			assertTrue(expected.getMessage().contains("2 labels"));
		}

		element = new TesterOptionList(elementForHtml("<select><option value=\"option.one.value\">option one</option><option value=\"option.two.value\">option two</option><option value=\"option.three.value\">option three</option></select>"));
		try {
			element.assertLabels("option one", "option two");
			throw new RuntimeException("more than expected");
		} catch (TesterElementError expected) {
			assertTrue(expected.getMessage().contains("2 labels"));
		}
	}

	public void testSize() {
		TesterOptionList element = new TesterOptionList(elementForHtml("<select></select>"));
		assertEquals(0, element.size());
		element.assertSize(0);
		try {
			element.assertSize(10);
			throw new RuntimeException("failure expected");
		} catch (TesterElementError expected) {}

		element = new TesterOptionList(elementForHtml("<select><option value=\"option.one.value\">option one</option><option value=\"option.two.value\">option two</option></select>"));
		assertEquals(2, element.size());
		element.assertSize(2);
	}

	public void testTesterOptions() {
		// make sure it can be constructed with no options
		new TesterOptionList(elementForHtml("<select></select>"));
	}
}
