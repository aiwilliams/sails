package org.opensails.sails.tester;

import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.PostEvent;
import org.opensails.sails.oem.SailsApplication;
import org.opensails.sails.servletapi.ShamHttpServletRequest;
import org.opensails.sails.servletapi.ShamHttpSession;
import org.opensails.sails.tester.oem.TestingDispatcher;
import org.opensails.sails.tester.oem.TestingHttpServletResponse;

public class TestableSailsApplication extends SailsApplication {
	protected ShamHttpSession session;

	public void configure(SailsTesterConfigurator configurator) {
		configureAndStart(configurator);
	}

	public TestGetEvent createGetEvent(String controller, String action) {
		ShamHttpServletRequest request = createRequest();
		request.setPathInfo(controller + "/" + action);
		TestingHttpServletResponse response = new TestingHttpServletResponse();
		TestGetEvent event = new TestGetEvent(this, request, response);
		((TestingDispatcher) dispatcher).installContainer(event);
		response.set(event);
		return event;
	}

	public TestPostEvent createPostEvent(String controller, String action, FormFields formFields) {
		ShamHttpServletRequest request = createRequest();
		request.setPathInfo(controller + "/" + action);
		request.setParameters(formFields.toMap());
		TestingHttpServletResponse response = new TestingHttpServletResponse();
		TestPostEvent event = new TestPostEvent(this, request, response);
		((TestingDispatcher) dispatcher).installContainer(event);
		response.set(event);
		return event;
	}

	public Page get(GetEvent event) {
		dispatcher.dispatch(event);
		return new Page(event);
	}

	public Page get(String controllerAction) {
		ShamHttpServletRequest request = createRequest();
		request.setPathInfo(controllerAction);
		TestingHttpServletResponse response = new TestingHttpServletResponse();
		TestGetEvent event = new TestGetEvent(this, request, response);
		((TestingDispatcher) dispatcher).installContainer(event);
		response.set(event);
		return get(event);
	}

	/**
	 * @return the current session. If create, creates one. If not, returns
	 *         null.
	 */
	public ShamHttpSession getSession(boolean create) {
		return (session == null && create) ? session = new ShamHttpSession() : session;
	}

	/**
	 * Gets rid of the current HttpSession. Any references to the old
	 * HttpSession are not 'managed' - that is, new requests will have a
	 * different session.
	 */
	public void newSession() {
		session = null;
	}

	public Page post(PostEvent event) {
		dispatcher.dispatch(event);
		return new Page(event);
	}

	public Page post(String controllerAction, FormFields formFields) {
		ShamHttpServletRequest request = createRequest();
		request.setPathInfo(controllerAction);
		request.setParameters(formFields.toMap());
		TestingHttpServletResponse response = new TestingHttpServletResponse();
		TestPostEvent event = new TestPostEvent(this, request, response);
		((TestingDispatcher) dispatcher).installContainer(event);
		response.set(event);
		return post(event);
	}

	/**
	 * @return a request that is bound to this application such that when a
	 *         session is created, we have it
	 */
	protected ShamHttpServletRequest createRequest() {
		return new ShamHttpServletRequest(session) {
			@Override
			public javax.servlet.http.HttpSession getSession() {
				return session = (ShamHttpSession) super.getSession();
			};

			@Override
			public javax.servlet.http.HttpSession getSession(boolean create) {
				return session = (ShamHttpSession) super.getSession(create);
			};
		};
	}
}
