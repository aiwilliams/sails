package org.opensails.sails.tester;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.oem.GetEvent;

public class TestGetEvent extends GetEvent {
	public TestGetEvent(ISailsApplication application, HttpServletRequest req, HttpServletResponse resp) {
		super(application, req, resp);
	}

	public void setActionParameters(String[] parameters) {
		url.setParameters(parameters);
	}

	@Override
	protected void containerSet() {
		super.containerSet();
		container.register(GetEvent.class, this);
	}

}
