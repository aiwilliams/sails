package org.opensails.sails.tester.form;

public class TesterSelectTest extends TesterElementTestCase {
	public void testConstructor() throws Exception {
		try {
			new TesterSelect(elementForHtml("<form></form>"), "select.name");
			throw new RuntimeException("expected failure");
		} catch (TesterElementError expected) {}
		try {
			new TesterSelect(elementForHtml("<form><select name=\"different.name\"></select></form>"), "select.name");
			throw new RuntimeException("expected failure");
		} catch (TesterElementError expected) {}
	}

	public void testLabeled() {
		TesterSelect element = new TesterSelect(elementForHtml("<form>  <label for=\"select.id\">label</label>  <select name=\"select.name\" id=\"select.id\"></select> </form>"), "select.name");
		element.assertLabeled("label");

		element = new TesterSelect(elementForHtml("<form>  <select name=\"select.name\" id=\"select.id\"></select> </form>"), "select.name");
		try {
			element.assertLabeled("label");
			throw new RuntimeException("expected failure");
		} catch (TesterElementError expected) {}
	}

	public void testOptionLabels() {
		TesterSelect element = new TesterSelect(elementForHtml("<form><select name=\"select.name\" id=\"select.id\"><option value=\"option.one.value\">option one</option><option value=\"option.two.value\">option two</option>  </select> </form>"), "select.name");
		assertNotNull(element.options());

		element = new TesterSelect(elementForHtml("<form><select name=\"select.name\" id=\"select.id\"></select> </form>"), "select.name");
		assertNotNull(element.options());
	}

}
