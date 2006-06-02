package org.opensails.sails.url;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.util.Quick;

public class ActionUrl extends ContextUrl<ActionUrl> {
	protected String action;
	protected String controller;
	protected List<Object> unadaptedParameters;

	public ActionUrl(ISailsEvent event) {
		super(event);
		this.unadaptedParameters = new ArrayList<Object>(2);
	}

	public ActionUrl(ISailsEvent event, String action) {
		this(event, event.getProcessorName(), action);
	}

	public ActionUrl(ISailsEvent event, String controller, String action) {
		this(event);
		this.controller = controller;
		this.action = action;
	}

	public void appendParameter(Object parameter) {
		unadaptedParameters.add(parameter);
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
		return controller == null ? event.getProcessorName() : controller;
	}

	@Override
	public String renderThyself() {
		return event.getResponse().encodeURL(super.renderThyself());
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public void setParameters(List parameters) {
		setParameters(parameters == null ? ArrayUtils.EMPTY_OBJECT_ARRAY : parameters.toArray());
	}

	public void setParameters(Object... args) {
		this.unadaptedParameters = Quick.list(args);
	}

	@Override
	protected String doRender() {
		return doRenderUrl(event.getUrl().getContextServlet());
	}

	protected String getParametersString() {
		if (unadaptedParameters.isEmpty()) return StringUtils.EMPTY;

		StringBuilder result = new StringBuilder();
		for (Object parameter : unadaptedParameters) {
			result.append("/");
			result.append(encode(event.forWebAsString(parameter)));
		}
		return result.toString();
	}

	@Override
	protected String renderAbsoluteUrl() {
		return doRenderUrl(event.getUrl().getAbsoluteServletUrl());
	}

	private String doRenderUrl(String servletPath) {
		StringBuilder u = new StringBuilder();
		u.append(servletPath);
		u.append("/");
		u.append(getControllerName());
		if (getActionName() != null) {
			u.append("/");
			u.append(getActionName());
		}
		u.append(getParametersString());
		u.append(queryParams);
		return u.toString();
	}
}
