package org.opensails.sails.url;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.ISailsEvent;

public class ActionUrl extends AbsoluteUrl<ActionUrl> {
	protected String action;
	protected String controller;
	protected Object[] unadaptedParameters;

	public ActionUrl(ISailsEvent event) {
		super(event);
	}

	public ActionUrl(ISailsEvent event, String controller, String action) {
		this(event);
		this.controller = controller;
		this.action = action;
	}

	public String getActionName() {
		return action;
	}

	/**
	 * @return the part of the url that is the controller/action
	 */
	public String getControllerAction() {
		String pathExtension = getControllerName();
		if (!StringUtils.isBlank(getActionName())) pathExtension += "/" + getActionName();
		return pathExtension;
	}

	public String getControllerName() {
		return controller == null ? event.getControllerName() : controller;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public void setParameters(List<?> parameters) {
		setParameters(parameters.toArray());
	}

	public void setParameters(Object... args) {
		this.unadaptedParameters = args;
	}

	// TODO: Implement parameter adapting
	protected String adapt(Object parameter) {
		return String.valueOf(parameter);
	}

	protected String getParametersString() {
		if (unadaptedParameters == null) return StringUtils.EMPTY;

		StringBuilder result = new StringBuilder();
		for (Object parameter : unadaptedParameters) {
			result.append("/");
			result.append(adapt(parameter));
		}
		return result.toString();
	}

	@Override
	protected String renderAbsoluteUrl() {
		return event.resolve(UrlType.CONTROLLER, getControllerAction() + getParametersString()).toString();
	}
}
