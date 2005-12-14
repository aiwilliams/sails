package org.opensails.sails.oem;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.ArrayUtils;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsEventListener;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.SailsException;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.form.FileUpload;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.url.EventUrl;
import org.opensails.sails.url.IEventUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;

public abstract class AbstractEvent implements ILifecycleEvent {
	protected ISailsApplication application;
	protected RequestContainer container;
	protected FormFields fields;
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

	public String[] getActionParameters() {
		return url.getActionParameters();
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

	public String getControllerName() {
		return url.getController();
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
		string.append(application.getName());
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
		this.url = new EventUrl(req);
		this.container = createContainer(parentContainer);
		this.container.register(FormFields.class, fields);
		containerSet();
	}
}
