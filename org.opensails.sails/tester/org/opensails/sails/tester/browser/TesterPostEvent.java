package org.opensails.sails.tester.browser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.event.oem.PostEvent;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.tester.form.TestFormFields;

public class TesterPostEvent extends PostEvent {
	public TesterPostEvent(ISailsApplication application, TesterRequestContainer container, HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
		this.application = application;
		this.container = container;
		initialize(application.getContainer());
	}

	public void setActionParameters(String[] parameters) {
		url.setParameters(parameters);
	}

	public void setQueryParameter(String name, String value) {
		url.setQueryParameter(name, forWebAsString(value));
	}

	@Override
	protected void containerSet() {
		super.containerSet();
		container.register(PostEvent.class, this);
	}

	@Override
	protected IEventContextContainer createContainer(IScopedContainer parentContainer) {
		return container;
	}

	@Override
	protected FormFields createFormFields(HttpServletRequest req) {
		return new TestFormFields(req);
	}
}
