package org.opensails.sails.url;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.event.ISailsEvent;

public class ActionUrl extends AbstractUrl<ActionUrl> {
	protected String action;
	protected String controller;
	protected Object[] unadaptedParameters;

	public ActionUrl(ISailsEvent event) {
		super(event);
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
		Object[] newParameters = new Object[unadaptedParameters.length + 1];
		System.arraycopy(unadaptedParameters, 0, newParameters, 0, unadaptedParameters.length);
		newParameters[unadaptedParameters.length] = parameter;
		unadaptedParameters = newParameters;
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

	public void setAction(String action) {
		this.action = action;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public void setParameters(List<?> parameters) {
		setParameters(parameters == null ? ArrayUtils.EMPTY_OBJECT_ARRAY : parameters.toArray());
	}

	public void setParameters(Object... args) {
		this.unadaptedParameters = args;
	}

	// TODO: Make this more robust - like what about things adapted to String[]
	@SuppressWarnings("unchecked")
	protected String adapt(Object parameter) {
		IAdapterResolver resolver = event.getContainer().instance(IAdapterResolver.class);
		IAdapter adapter = resolver.resolve(parameter.getClass(), event.getContainer());
		return String.valueOf(adapter.forWeb(new AdaptationTarget<Object>((Class<Object>) parameter.getClass()), parameter));
	}

	@Override
	protected String doRender() {
		// TODO: this is not correct, but maintains previous behavior. It should
		// not render absolute, as controller/action is realative to the server.
		return renderAbsoluteUrl();
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
		IUrl absolute = event.resolve(UrlType.CONTROLLER, getControllerAction() + getParametersString());
		return event.getResponse().encodeURL(absolute.toString());
	}
}
