package org.opensails.sails.tester;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import junit.framework.*;

import org.opensails.sails.*;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.controller.oem.*;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.*;
import org.opensails.sails.http.*;
import org.opensails.sails.oem.*;
import org.opensails.sails.tester.form.*;
import org.opensails.sails.tester.html.*;
import org.opensails.sails.tester.oem.*;
import org.opensails.sails.tester.servletapi.*;
import org.opensails.sails.tester.util.*;
import org.opensails.sails.util.*;

public class Page {
	protected final ISailsEvent event;
	protected HttpServletRequest request;
	protected ShamHttpServletResponse response;

	public Page(ISailsEvent event) {
		this.event = event;
		this.request = event.getRequest();
		this.response = (ShamHttpServletResponse) event.getResponse();
	}

	public void assertContains(String exactString) throws AssertionFailedError {
		assertContains("", exactString);
	}

	public void assertContains(String message, String exactString) throws AssertionFailedError {
		assertPageExpectation(message + " Expected " + url() + " to contain <" + exactString + ">", contains(exactString));
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
		Assert.assertEquals(expected.toHttpValue(), response.getContentType());
	}

	public void assertExcludes(String exactString) {
		assertPageExpectation("Expected " + url() + " not to contain <" + exactString + ">", !contains(exactString));
	}

	public void assertLayout(String expected) throws AssertionFailedError {
		TemplateActionResult result = container().instance(TemplateActionResult.class);
		if (result == null) {
			if (expected != null) throw new AssertionFailedError("A template was not rendered");
			else return; // not template, layout expected to be null, so no
			// problem
		}
		Assert.assertEquals("Layout was not rendered as expected", expected, result.getLayout());
	}

	public void assertMatches(String regex) {
		assertMatches("", regex);
	}

	public void assertMatches(String message, String regex) {
		assertPageExpectation(message + " Expected " + url() + " to match <" + regex + ">", RegexHelper.containsMatch(source(), regex));
	}

	public void assertRenders() throws AssertionFailedError {
		try {
			source();
		} catch (Throwable notExpected) {
			StringBuilder message = new StringBuilder();
			Throwable cause = notExpected;
			do {
				message.append(cause);
				cause = cause.getCause();
				if (cause != null) message.append(" caused by\n");
			} while (cause != null);
			Assert.fail(String.format("Page did not render successfully\n%s", message));
		}
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
	 * @return the RequestContainer of the ISailsEvent that generated this page
	 */
	public RequestContainer container() {
		return event.getContainer();
	}

	public boolean contains(String exactString) {
		return source().contains(exactString);
	}

	public ExposedObject exposed(String nameAsExposed) {
		return new ExposedObject(container().instance(TestingBinding.class).get(nameAsExposed));
	}

	/**
	 * @param id
	 * @return a FieldSet anywhere on this page
	 */
	public FieldSet fieldSet(String id) {
		return new FieldSet(source(), id);
	}

	public TestFlash flash() {
		return new TestFlash(container().instance(Flash.class));
	}

	public Form form() {
		HtmlForm htmlForm = container().instance(HtmlForm.class);
		if (htmlForm != null) return new Form(source(), htmlForm);
		else return new Form(source());
	}

	public boolean matches(String regex) {
		return source().matches(regex);
	}

	public TestRedirectUrl redirectUrl() {
		return null;
	}

	/**
	 * @return the html source
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
			writer.write(" ^^^^^^^^^^^^^^^^^^^");
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
				stringWriter.write(" See source below:\n\n");
			} catch (Exception e) {
				throw new SailsException("Couldn't write to System.err");
			}
			viewSource(stringWriter);
			Assert.fail(message);
		}
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
