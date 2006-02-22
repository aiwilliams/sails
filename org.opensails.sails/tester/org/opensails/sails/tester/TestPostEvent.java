package org.opensails.sails.tester;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.event.oem.PostEvent;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.tester.form.TestFormFields;

public class TestPostEvent extends PostEvent {
	public TestPostEvent(ISailsApplication application, TestRequestContainer container, HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
		this.application = application;
		this.container = container;
		initialize(application.getContainer());
	}

	public void setActionParameters(String[] parameters) {
		url.setParameters(parameters);
	}

	@Override
	protected void containerSet() {
		super.containerSet();
		container.register(PostEvent.class, this);
	}

	@Override
	protected RequestContainer createContainer(ScopedContainer parentContainer) {
		return container;
	}

	@Override
	protected FormFields createFormFields(HttpServletRequest req) {
		return new TestFormFields(req);
	}
}
