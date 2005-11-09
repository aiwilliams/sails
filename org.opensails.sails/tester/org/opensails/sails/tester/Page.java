package org.opensails.sails.tester;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.SailsException;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.tester.form.Form;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;
import org.opensails.sails.util.RegexHelper;

public class Page {
	protected final ISailsEvent event;
	protected ShamHttpServletRequest request;
	protected ShamHttpServletResponse response;

	public Page(ISailsEvent event) {
		this.event = event;
		this.request = (ShamHttpServletRequest) event.getRequest();
		this.response = (ShamHttpServletResponse) event.getResponse();
	}

	public void assertContains(String exactString) throws AssertionFailedError {
		assertContains("", exactString);
	}

	public void assertContains(String message, String exactString) throws AssertionFailedError {
		assertPageExpectation(message + " Expected " + getUrl() + " to contain <" + exactString + ">", response.getWrittenContent().contains(exactString));
	}

	public void assertContentType(ContentType expected) throws AssertionFailedError {
		Assert.assertEquals(expected.toHttpValue(), response.getContentType());
	}

	public void assertMatches(String regex) {
		assertMatches("", regex);
	}

	public void assertMatches(String message, String regex) {
		assertPageExpectation(message + " Expected " + getUrl() + " to match <" + regex + ">", RegexHelper.containsMatch(response.getWrittenContent(), regex));
	}

	public boolean contains(String exactString) {
		return response.getWrittenContent().contains(exactString);
	}

	public ScopedContainer getContainer() {
		return event.getContainer();
	}

	public Form getForm() {
		HtmlForm htmlForm = getContainer().instance(HtmlForm.class);
		if (htmlForm != null) return new Form(response.getWrittenContent(), htmlForm);
		else return new Form(response.getWrittenContent());
	}

	public TestRedirectUrl getRedirectUrl() {
		return null;
	}

	public TestUrl getUrl() {
		return new TestUrl(event.getEventUrl());
	}

	public boolean matches(String regex) {
		return response.getWrittenContent().matches(regex);
	}

	public void viewSource(Writer writer) {
		try {
			writer.write("vvvvvvvvvvvvvvvvvv begin response to ");
			writer.write(event.getEventUrl().getActionUrl());
			writer.write(" vvvvvvvvvvvvvvvvvv\n");
			writer.write(response.getWrittenContent());
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
