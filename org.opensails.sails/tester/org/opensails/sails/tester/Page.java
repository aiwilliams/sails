package org.opensails.sails.tester;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.http.HttpHeader;
import org.opensails.sails.oem.Flash;
import org.opensails.sails.tester.form.TesterFieldset;
import org.opensails.sails.tester.form.TesterForm;
import org.opensails.sails.tester.oem.LazyActionResultProcessor;
import org.opensails.sails.tester.oem.TestingBinding;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;
import org.opensails.sails.tester.util.CollectionAssert;
import org.opensails.sails.util.RegexHelper;

public class Page {
	protected final ISailsEvent event;

	protected HttpServletRequest request;
	protected ShamHttpServletResponse response;

	private Document document;

	public Page(ISailsEvent event) {
		this.event = event;
		this.request = event.getRequest();
		this.response = (ShamHttpServletResponse) event.getResponse();
	}

	public void assertContains(String exactString) throws AssertionFailedError {
		assertContains("", exactString);
	}

	public void assertContains(String message, String exactString) throws AssertionFailedError {
		String finalMessage = "Expected " + url() + " to contain <" + exactString + ">";
		if (!StringUtils.isBlank(message)) finalMessage = message + " ";
		finalMessage = "\n" + finalMessage;
		assertPageExpectation(finalMessage, contains(exactString));
	}

	public void assertContainsInOrder(String... strings) {
		String source = source();
		int index = 0;
		for (String string : strings) {
			index = source.indexOf(string, index);
			if (index == -1) assertPageExpectation("Expected " + url() + " to contain <" + string + "> in a particular place", false);
		}
	}

	public void assertContentType(ContentType expected) throws AssertionFailedError {
		assertHeaderEquals(expected);
	}

	/**
	 * Asserts that the content matches completely and exactly
	 * 
	 * @param expected
	 */
	public void assertEquals(String expected) {
		Assert.assertEquals("Page content should match exactly", expected, source());
	}

	public void assertExcludes(String exactString) {
		assertPageExpectation("Expected " + url() + " not to contain <" + exactString + ">", !contains(exactString));
	}

	public void assertHeaderEquals(HttpHeader expected) {
		String header = expected.name();
		String expectedValue = expected.value();
		String headerValue = response.getHeader(header);
		assertHeaderEquals(header, expectedValue, headerValue);
	}

	public void assertHeaderEquals(String header, String expected, String actual) {
		ensureProcessingComplete();
		Assert.assertEquals(String.format("Expected header %s to match value %s but was ", header, expected, actual), expected, actual);
	}

	public void assertLayout(String expected) throws AssertionFailedError {
		TemplateActionResult result = container().instance(TemplateActionResult.class);
		if (result == null) {
			if (expected != null) throw new AssertionFailedError("A template was not rendered");
			else return; // !template, layout == null
		}
		Assert.assertEquals("Layout was not rendered ", expected, result.getLayout());
	}

	public void assertMatches(String regex) {
		assertMatches("", regex);
	}

	public void assertMatches(String message, String regex) {
		assertPageExpectation(message + " Expected " + url() + " to match <" + regex + ">", RegexHelper.containsMatch(source(), regex));
	}

	/**
	 * @throws AssertionFailedError if the page does render successfully
	 */
	public void assertRenderFails() throws AssertionFailedError {
		assertRenderFails("Expected render to fail");
	}

	/**
	 * @param message
	 * @throws AssertionFailedError if the page does render successfully
	 */
	public void assertRenderFails(String message) throws AssertionFailedError {
		boolean rendered = false;
		try {
			source();
			rendered = true;
		} catch (Throwable expected) {}
		if (rendered) Assert.fail(message);
	}

	public void assertRenders() throws AssertionFailedError {
		ensureNotRedirected("Browser was redirected. No render could have occurred.");
		source();
	}

	public void assertResponseHeader(String headerName, String expected) throws AssertionFailedError {
		Assert.assertEquals(expected, response.getHeader(headerName));
	}

	public void assertTemplate(String expected) {
		TemplateActionResult result = container().instance(TemplateActionResult.class);
		if (result == null) {
			if (expected != null) throw new AssertionFailedError("A template was not rendered");
			else return; // not template, layout expected to be null, so no
			// problem
		}
		Assert.assertEquals("Template was not rendered as expected", expected, result.getIdentifier());
	}

	/**
	 * @return the container of the ISailsEvent that generated this page
	 */
	public IEventContextContainer container() {
		return event.getContainer();
	}

	public boolean contains(String exactString) {
		return source().contains(exactString);
	}

	public ExposedObject exposed(String nameAsExposed) {
		ensureNotRedirected("Browser was redirected. Exposed objects are not available");
		return new ExposedObject(container().instance(TestingBinding.class).get(nameAsExposed));
	}

	/**
	 * @param id
	 * @return a FieldSet anywhere on this page
	 */
	public TesterFieldset fieldSet(String id) {
		return new TesterFieldset(rootElement(), id);
	}

	public TestFlash flash() {
		return new TestFlash(container().instance(Flash.class));
	}

	public TesterForm form() {
		return new TesterForm(rootElementOrNull(), container().instance(HtmlForm.class));
	}

	public TesterForm form(String name) {
		return new TesterForm(rootElementOrNull(), name, container().instance(HtmlForm.class));
	}

	public Document getDocument() {
		if (document == null) {
			try {
				StringBuilder parsableSource = new StringBuilder();
				parsableSource.append("<page>");
				parsableSource.append(source());
				parsableSource.append("</page>");
				document = DocumentHelper.parseText(parsableSource.toString());
			} catch (Exception e) {
				throw new SailsException("The page is not a valid document", e);
			}
		}
		return document;
	}

	public boolean matches(String regex) {
		return source().matches(regex);
	}

	public TestRedirectUrl redirectUrl() {
		return new TestRedirectUrl(event, response);
	}

	public TesterScriptList scripts() {
		return new TesterScriptList(getDocument());
	}

	/**
	 * Provides access to the written content.
	 * <p>
	 * This method makes no assertions. It is intended to support the many
	 * assertions on this class. If you find yourself accessing the source
	 * directly, please do consider whether any of the existing assertions will
	 * give you what you need. If they don't, we would appreciate you sharing
	 * your struggles on the user mailing list.
	 * 
	 * @return the html source, empty String if nothing has been written
	 */
	public String source() {
		return response.getWrittenContent();
	}

	public TestUrl url() {
		return new TestUrl(event.getEventUrl());
	}

	public void viewSource(Writer writer) {
		try {
			writer.write("vvvvvvvvvvvvvvvvvv begin response to ");
			writer.write(event.getEventUrl().getActionUrl());
			writer.write(" vvvvvvvvvvvvvvvvvv\n");
			writer.write(source());
			writer.write("\n^^^^^^^^^^^^^^^^^^^ end response to ");
			writer.write(event.getEventUrl().getActionUrl());
			writer.write(" ^^^^^^^^^^^^^^^^^^^\n\n");
			writer.flush();
		} catch (IOException e) {
			throw new SailsException("Unable to view source of page", e);
		}
	}

	/**
	 * If expected is not true, dump the content of the current browser page to
	 * System.err and fail.
	 */
	protected void assertPageExpectation(String message, boolean expected) {
		if (!expected) {
			OutputStreamWriter stringWriter = new OutputStreamWriter(System.err);
			try {
				stringWriter.write(message);
				stringWriter.write(" See source below:\n");
				viewSource(stringWriter);
			} catch (Exception e) {
				throw new SailsException("Couldn't write to System.err");
			}
			Assert.fail(message);
		}
	}

	protected void ensureNotRedirected(String message) throws AssertionFailedError {
		if (response.wasRedirected()) throw new AssertionFailedError(message);
	}

	protected void ensureProcessingComplete() {
		processor().doProcess();
	}

	protected LazyActionResultProcessor processor() {
		return event.getContainer().instance(LazyActionResultProcessor.class);
	}

	protected Element rootElement() {
		return getDocument().getRootElement();
	}

	protected Element rootElementOrNull() {
		Element root = null;
		if (!StringUtils.isBlank(source())) root = rootElement();
		return root;
	}

	public class ExposedObject {
		public Object value;

		public ExposedObject(Object value) {
			this.value = value;
		}

		// TODO: Make this support all the possible things the exposed value
		// might be (Collection<T>, T[], etc)
		@SuppressWarnings("unchecked")
		public <T> void assertContainsOnly(T[] expected) {
			CollectionAssert.containsOnly(expected, (Collection<T>) value);
		}

		public void assertEquals(Object expectedValue) {
			Assert.assertEquals(expectedValue, value);
		}

		public void assertExists() {
			Assert.assertNotNull(value);
		}
	}
}
