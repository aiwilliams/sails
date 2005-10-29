package org.opensails.sails.tester.form;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class SelectTest extends TestCase {
	public void testConstructor() throws Exception {
		new Select("<form><select name=\"select.name\"></select></form>", "select.name");
		try {
			new Select("<form></form>", "select.name");
			throw new RuntimeException("expected failure");
		} catch (AssertionFailedError expected) {}
		try {
			new Select("<form><select name=\"different.name\"></select></form>", "select.name");
			throw new RuntimeException("expected failure");
		} catch (AssertionFailedError expected) {}
	}

	public void testLabeled() {
		Select element = new Select("<form>  <label for=\"select.id\">label</label>  <select name=\"select.name\" id=\"select.id\"></select> </form>", "select.name");
		element.labeled("label");

		element = new Select("<form>  <select name=\"select.name\" id=\"select.id\"></select> </form>", "select.name");
		try {
			element.labeled("label");
			throw new RuntimeException("expected failure");
		} catch (AssertionFailedError expected) {}
	}

	public void testOptionLabels() {
		Select element = new Select("<form><select name=\"select.name\" id=\"select.id\"><option value=\"option.one.value\">option one</option><option value=\\\"option.two.value\\\">option two</option>  </select> </form>", "select.name");
		assertNotNull(element.options());

		element = new Select("<form><select name=\"select.name\" id=\"select.id\"></select> </form>", "select.name");
		assertNotNull(element.options());
	}
}
