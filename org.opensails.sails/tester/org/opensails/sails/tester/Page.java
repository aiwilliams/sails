package org.opensails.sails.tester;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.SailsException;
import org.opensails.sails.controller.oem.TemplateActionResult;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.oem.Flash;
import org.opensails.sails.tester.form.Form;
import org.opensails.sails.tester.html.FieldSet;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;
import org.opensails.sails.util.RegexHelper;

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

	/**
	 * @return the RequestContainer of the ISailsEvent that generated this page
	 */
	public RequestContainer container() {
		return event.getContainer();
	}

	public boolean contains(String exactString) {
		return source().contains(exactString);
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
}
