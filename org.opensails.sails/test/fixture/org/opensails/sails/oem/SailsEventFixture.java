package org.opensails.sails.oem;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.Sails;
import org.opensails.sails.controller.IController;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.servletapi.ShamHttpServletRequest;
import org.opensails.sails.servletapi.ShamHttpServletResponse;

public class SailsEventFixture {
	public static AbstractEvent abstractEvent(ISailsApplication application) {
		AbstractEvent event = new AbstractEvent(application, new ShamHttpServletRequest(), new ShamHttpServletResponse()) {};
		setContainer(event, application);
		return event;
	}

	public static ExceptionEvent actionException(Class<? extends IController> originatingController, String originatingAction) {
		return actionException(Sails.controllerName(originatingController), originatingAction);
	}

	public static ExceptionEvent actionException(String originatingController, String originatingAction) {
		GetEvent originatingEvent = actionGet(originatingController, originatingAction);
		return new ExceptionEvent(originatingEvent, new RuntimeException("Created in SailsEventFixture#actionException(String, String)"));
	}

	public static GetEvent actionGet() {
		return actionGet("controller", "action");
	}

	public static GetEvent actionGet(Class<? extends IController> controller, String action) {
		return actionGet(Sails.controllerName(controller), action);
	}

	public static GetEvent actionGet(String controller, String action) {
		ShamHttpServletRequest request = new ShamHttpServletRequest(ShamHttpServletRequest.GET);
		setControllerAction(request, controller, action);
		ISailsApplication application = SailsApplicationFixture.basic();
		GetEvent event = new GetEvent(application, request, new ShamHttpServletResponse());
		return setContainer(event, application);
	}

	public static PostEvent actionPost(FormFields fields) {
		return actionPost("controller", "action", fields);
	}

	public static PostEvent actionPost(String controller, String action) {
		ISailsApplication application = SailsApplicationFixture.basic();
		PostEvent event = new PostEvent(application, setControllerAction(new ShamHttpServletRequest(), controller, action), new ShamHttpServletResponse());
		return setContainer(event, application);
	}

	public static PostEvent actionPost(String controller, String action, FormFields formFields) {
		ShamHttpServletRequest request = new ShamHttpServletRequest(ShamHttpServletRequest.POST);
		request.setParameters(formFields.toMap());
		setControllerAction(request, controller, action);
		ISailsApplication application = SailsApplicationFixture.basic();
		PostEvent event = new PostEvent(application, request, new ShamHttpServletResponse());
		return setContainer(event, application);
	}

	public static String getImagePath(ISailsEvent event, String image) {
		return event.getRequest().getContextPath() + "/" + event.getConfiguration().getString(Sails.ConfigurationKey.Url.IMAGES) + "/" + image;
	}

	/**
	 * Sets a container onto the event. The container is a new instance for
	 * every call.
	 */
	public static <T extends ILifecycleEvent> T setContainer(T event) {
		event.setContainer(new ScopedContainer(ApplicationScope.REQUEST));
		return event;
	}

	/**
	 * Sets a container onto the event. The container is a new instance for
	 * every call. The parent is that of the application.
	 */
	public static <T extends ILifecycleEvent> T setContainer(T event, ISailsApplication application) {
		event.setContainer(application.getContainer().makeChild(ApplicationScope.REQUEST));
		return event;
	}

	public static ShamHttpServletRequest setControllerAction(ShamHttpServletRequest request, String controller, String action) {
		request.setPathInfo(String.format("%s/%s", controller, action));
		return request;
	}

	public static ShamEvent sham() {
		ShamEvent event = new ShamEvent();
		setContainer(event);
		return event;
	}

	/**
	 * An 'unknown' ILifecycleEvent subclass
	 */
	public static ILifecycleEvent unknown() {
		return setContainer(new AbstractEvent(SailsApplicationFixture.basic(), new ShamHttpServletRequest(), new ShamHttpServletResponse()) {});
	}
}
