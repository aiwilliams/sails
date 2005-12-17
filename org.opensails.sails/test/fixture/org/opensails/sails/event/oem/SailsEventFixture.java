package org.opensails.sails.event.oem;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.Sails;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.ISailsEventConfigurator;
import org.opensails.sails.event.oem.AbstractEvent;
import org.opensails.sails.event.oem.ExceptionEvent;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.ILifecycleEvent;
import org.opensails.sails.event.oem.PostEvent;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.SailsApplicationFixture;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class SailsEventFixture {
	public static AbstractEvent abstractEvent() {
		return abstractEvent(SailsApplicationFixture.basic());
	}

	public static AbstractEvent abstractEvent(ISailsApplication application) {
		return new AbstractEvent(application, application.getContainer(), new ShamHttpServletRequest(), new ShamHttpServletResponse()) {};
	}

	public static ExceptionEvent actionException(Class<? extends IControllerImpl> originatingController, String originatingAction) {
		return actionException(Sails.controllerName(originatingController), originatingAction);
	}

	public static ExceptionEvent actionException(String originatingController, String originatingAction) {
		GetEvent originatingEvent = actionGet(originatingController, originatingAction);
		return new ExceptionEvent(originatingEvent, new RuntimeException("Created in SailsEventFixture#actionException(String, String)"));
	}

	public static GetEvent actionGet() {
		return actionGet("controller", "action");
	}

	public static GetEvent actionGet(Class<? extends IControllerImpl> controller, String action) {
		return actionGet(Sails.controllerName(controller), action);
	}

	public static GetEvent actionGet(String controller, String action) {
		ShamHttpServletRequest request = new ShamHttpServletRequest(ShamHttpServletRequest.GET);
		setControllerAction(request, controller, action);
		ISailsApplication application = SailsApplicationFixture.basic();
		GetEvent getEvent = new GetEvent(application, request, new ShamHttpServletResponse());
		application.getContainer().instance(ISailsEventConfigurator.class).configure(getEvent, getEvent.getContainer());
		return getEvent;
	}

	public static PostEvent actionPost(FormFields fields) {
		return actionPost("controller", "action", fields);
	}

	public static PostEvent actionPost(String controller, String action) {
		ISailsApplication application = SailsApplicationFixture.basic();
		return new PostEvent(application, setControllerAction(new ShamHttpServletRequest(), controller, action), new ShamHttpServletResponse());
	}

	public static PostEvent actionPost(String controller, String action, FormFields formFields) {
		ShamHttpServletRequest request = new ShamHttpServletRequest(ShamHttpServletRequest.POST);
		request.setParameters(formFields.toMap());
		setControllerAction(request, controller, action);
		ISailsApplication application = SailsApplicationFixture.basic();
		return new PostEvent(application, request, new ShamHttpServletResponse());
	}

	public static String getImagePath(ISailsEvent event, String image) {
		return event.getRequest().getContextPath() + "/" + event.getConfiguration().getString(Sails.ConfigurationKey.Url.IMAGES) + "/" + image;
	}

	public static ShamHttpServletRequest setControllerAction(ShamHttpServletRequest request, String controller, String action) {
		request.setPathInfo(String.format("%s/%s", controller, action));
		return request;
	}

	public static ShamEvent sham() {
		return new ShamEvent();
	}

	/**
	 * An 'unknown' ILifecycleEvent subclass
	 */
	public static ILifecycleEvent unknown() {
		ISailsApplication app = SailsApplicationFixture.basic();
		return new AbstractEvent(app, app.getContainer(), new ShamHttpServletRequest(), new ShamHttpServletResponse()) {};
	}
}
