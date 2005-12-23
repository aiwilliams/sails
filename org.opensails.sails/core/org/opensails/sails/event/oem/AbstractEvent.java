package org.opensails.sails.event.oem;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.configuration.*;
import org.apache.commons.lang.*;
import org.opensails.rigging.*;
import org.opensails.sails.*;
import org.opensails.sails.action.*;
import org.opensails.sails.action.oem.*;
import org.opensails.sails.adapter.*;
import org.opensails.sails.event.*;
import org.opensails.sails.form.*;
import org.opensails.sails.url.*;
import org.opensails.sails.util.*;

public abstract class AbstractEvent implements ILifecycleEvent {
	protected ISailsApplication application;
	protected RequestContainer container;
	protected FormFields fields;
	protected ActionParameterList parameters;
	protected final HttpServletRequest req;
	protected final HttpServletResponse response;

	protected OutputStream responseOutputStream;
	protected PrintWriter responseWriter;
	protected EventUrl url;

	public AbstractEvent(ISailsApplication application, ScopedContainer parentContainer, HttpServletRequest req, HttpServletResponse resp) {
		this(req, resp);
		this.application = application;
		initialize(parentContainer);
	}

	// allow some control for subclasses
	protected AbstractEvent(HttpServletRequest req, HttpServletResponse resp) {
		this.req = new EventServletRequest(this, req);
		this.response = resp;
		this.fields = new FormFields(req);
	}

	public void beginDispatch() {
		container.start();
		getBroadcaster().beginDispatch(this);
	}

	public void endDispatch() {
		getBroadcaster().endDispatch(this);
		container.stop();
		container.dispose();
	}

	public String getActionName() {
		return url.getAction();
	}

	public ActionParameterList getActionParameters() {
		if (parameters == null) parameters = createParameters(url.getActionParameters());
		return parameters;
	}

	public ISailsApplication getApplication() {
		return application;
	}

	public Configuration getConfiguration() {
		return application.getConfiguration();
	}

	public RequestContainer getContainer() {
		return container;
	}

	public IEventUrl getEventUrl() {
		return url;
	}

	public String getFieldValue(String name) {
		return fields.value(name);
	}

	public String[] getFieldValues(String name) {
		return fields.values(name);
	}

	public FileUpload getFileUpload(String name) {
		return fields.file(name);
	}

	public String getProcessorName() {
		return url.getController();
	}

	public HttpServletRequest getRequest() {
		return req;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the OutputStream of the response
	 * @throws IllegalStateException if this gets called AFTER
	 *         #getResponseWriter()
	 */
	public OutputStream getResponseOutputStream() {
		if (responseOutputStream == null) {
			try {
				responseOutputStream = response.getOutputStream();
			} catch (IOException e) {
				throw new SailsException("Unable to obtain output stream of response", e);
			}
		}
		return responseOutputStream;
	}

	/**
	 * @return a PrintWriter from the response
	 * @throws IllegalStateException if an output stream was obtained first
	 */
	public PrintWriter getResponseWriter() {
		if (responseWriter == null) {
			try {
				responseWriter = getResponse().getWriter();
			} catch (IOException e) {
				throw new SailsException("Unable to obtain a writer on the response", e);
			}
		}
		return responseWriter;
	}

	public HttpSession getSession(boolean create) {
		return req.getSession(create);
	}

	public IUrl resolve(UrlType urlType, String urlFragment) {
		IUrlResolver resolver = getContainer().instance(IUrlResolver.class);
		return resolver.resolve(urlType, this, urlFragment);
	}

	public void sessionCreated(HttpSession session) {
		getBroadcaster().sessionCreated(this, session);
	}

	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		string.append(ClassHelper.getName(this.getClass()));
		string.append(" in ");
		string.append(application.getName());
		string.append(" Application");
		string.append(" [");
		string.append(getEventUrl().getActionUrl());
		string.append("]");
		return string.toString();
	}

	/**
	 * @inheritDoc
	 * 
	 * Subclasses should override if the desire to indicate a specific
	 * Controller#process() method. This implementation will cause the generic
	 * ISailsEvent method to be invoked.
	 */
	public IActionResult visit(IActionEventProcessor eventProcessor) {
		return eventProcessor.process(this);
	}

	/**
	 * Appends content to the body of the response to the client.
	 * 
	 * @param text
	 */
	public void write(String text) {
		getResponseWriter().append(text);
	}

	/**
	 * Once the container has been validated and assigned, this is called from
	 * {@link #setContainer(ScopedContainer)}. Subclasses may override.
	 * 
	 * @param container
	 */
	protected void containerSet() {}

	protected RequestContainer createContainer(ScopedContainer parentContainer) {
		return new RequestContainer(parentContainer, this);
	}

	/**
	 * Called once, lazily, when getActionParameters is invoked first time
	 * 
	 * @return the ActionParameters of this event
	 */
	protected ActionParameterList createParameters(String[] eventParameters) {
		return new ActionParameterList(eventParameters, getAdapterResolver());
	}

	protected ContainerAdapterResolver getAdapterResolver() {
		return getContainer().instance(ContainerAdapterResolver.class);
	}

	protected ISailsEventListener getBroadcaster() {
		return application.getContainer().broadcast(ISailsEventListener.class, false);
	}

	/**
	 * @param prefix
	 * @return all field names with prefix, minus the prefix itself
	 */
	@SuppressWarnings("unchecked")
	protected String[] getFieldNamesPrefixed(String prefix) {
		if (fields.isEmpty()) return ArrayUtils.EMPTY_STRING_ARRAY;
		List<String> fieldNames = new ArrayList<String>(5);
		for (String key : fields.fieldNames())
			if (key.startsWith(prefix)) fieldNames.add(key.substring(prefix.length()));
		return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
	}

	protected void initialize(ScopedContainer parentContainer) {
		url = new EventUrl(req);
		container = createContainer(parentContainer);
		container.register(FormFields.class, fields);
		containerSet();
	}
}
