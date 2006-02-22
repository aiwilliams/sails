package org.opensails.sails.tester.form;

import javax.servlet.http.HttpServletRequest;

import org.opensails.sails.event.oem.EventServletRequest;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;

/**
 * Answered by the Page to allow for assertions against the form fields. Also
 * makes simulated file uploads possible.
 * 
 * @author aiwilliams
 */
public class TestFormFields extends FormFields {
	public TestFormFields(HttpServletRequest request) {
		super(request);
	}

	protected ShamHttpServletRequest asShamRequest(HttpServletRequest request) {
		EventServletRequest wrapper = ((EventServletRequest) request);
		ShamHttpServletRequest shamRequest = (ShamHttpServletRequest) wrapper.getRequest();
		return shamRequest;
	}

	@Override
	protected void initializeFromMultipart(HttpServletRequest request) {
		this.backingMap = asShamRequest(request).getMultipartParameters();
	}

	@Override
	protected boolean isMultipartRequest(HttpServletRequest request) {
		return asShamRequest(request).isMultipartRequest();
	}
}
