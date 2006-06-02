package org.opensails.sails.event.oem;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.ActionParameterList;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.event.ISailsEventListener;
import org.opensails.sails.form.FileUpload;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.url.EventUrl;
import org.opensails.sails.url.IEventUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;
import org.opensails.spyglass.SpyGlass;

public abstract class AbstractEvent implements ILifecycleEvent {
	protected ISailsApplication application;
	protected IEventContextContainer container;
	protected FormFields fields;
	protected ActionParameterList parameters;
	protected final HttpServletRequest request;
	protected final HttpServletResponse response;

	protected OutputStream responseOutputStream;
	protected PrintWriter responseWriter;
	protected EventUrl url;

	protected List<OutputStream> outputRecorders;;

	public AbstractEvent(ISailsApplication application, IScopedContainer parentContainer, HttpServletRequest req, HttpServletResponse resp) {
		this(req, resp);
		this.application = application;
		initialize(parentContainer);
	}

	// allow some control for subclasses
	protected AbstractEvent(HttpServletRequest req, HttpServletResponse resp) {
		this.request = new EventServletRequest(this, req);
		this.response = resp;
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

	@SuppressWarnings("unchecked")
	public String forWebAsString(Object value) {
		IAdapterResolver resolver = getContainer().instance(IAdapterResolver.class);
		IAdapter adapter = resolver.resolve(value.getClass(), getContainer());
		return String.valueOf(adapter.forWeb(new AdaptationTarget<Object>((Class<Object>) value.getClass()), value));
	};

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

	public IEventContextContainer getContainer() {
		return container;
	}

	public String getContextIdentifier() {
		return String.format("%s/%s", getProcessorName(), getActionName());
	}

	public IEventUrl getUrl() {
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

	public FormFields getFormFields() {
		return fields;
	}

	public String getProcessorName() {
		return url.getController();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public OutputStream getResponseOutputStream() {
		if (responseOutputStream == null) {
			try {
				if (outputRecorders == null || outputRecorders.isEmpty()) responseOutputStream = response.getOutputStream();
				else responseOutputStream = new RecordingOutputStream(outputRecorders, response.getOutputStream());
			} catch (IOException e) {
				throw new SailsException("Unable to obtain output stream of response", e);
			}
		}
		return responseOutputStream;
	}

	public PrintWriter getResponseWriter() {
		if (responseWriter == null) {
			try {
				if (outputRecorders == null || outputRecorders.isEmpty()) responseWriter = getResponse().getWriter();
				else responseWriter = new RecordingPrintWriter(getResponse().getWriter());
			} catch (IOException e) {
				throw new SailsException("Unable to obtain a writer on the response", e);
			}
		}
		return responseWriter;
	}

	public HttpSession getSession(boolean create) {
		return request.getSession(create);
	}

	@SuppressWarnings("unchecked")
	public <T> T instance(Class<T> key) {
		return (T) getContainer().instance(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T instance(Class<T> key, Class defaultImplementation) {
		return (T) getContainer().instance(key, defaultImplementation);
	}

	public void recordOutput(OutputStream recorder) throws IllegalStateException {
		if (this.responseOutputStream != null || this.responseWriter != null) throw new IllegalStateException("Output recorders must be registered before anything uses the response output stream in any way");
		if (outputRecorders == null) outputRecorders = new ArrayList<OutputStream>();
		outputRecorders.add(recorder);
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
		string.append(SpyGlass.getName(this.getClass()));
		string.append(" in ");
		string.append(application.getName());
		string.append(" Application");
		string.append(" [");
		string.append(getUrl().getActionUrl());
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
	 * Once the container has been validated and assigned, this is called during
	 * event initialization, right after the container has been set. Subclasses
	 * may override.
	 */
	protected void containerSet() {}

	protected IEventContextContainer createContainer(IScopedContainer parentContainer) {
		return new RequestContainer(parentContainer, this);
	}

	protected FormFields createFormFields(HttpServletRequest req) {
		return new FormFields(req);
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

	protected void initialize(IScopedContainer parentContainer) {
		url = new EventUrl(request);
		container = createContainer(parentContainer);
		fields = createFormFields(request);
		container.register(FormFields.class, fields);
		containerSet();
	}

	protected static class RecordingOutputStream extends OutputStream {
		protected List<OutputStream> recorders;
		protected OutputStream destination;

		protected RecordingOutputStream(List<OutputStream> recorders, OutputStream destination) {
			this.recorders = recorders;
			this.destination = destination;
		}

		@Override
		public void write(byte[] b) throws IOException {
			for (OutputStream recorder : recorders)
				recorder.write(b);
			destination.write(b);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			for (OutputStream recorder : recorders)
				recorder.write(b, off, len);
			destination.write(b, off, len);
		}

		@Override
		public void write(int b) throws IOException {
			for (OutputStream recorder : recorders)
				recorder.write(b);
			destination.write(b);
		}
	}

	protected class RecordingPrintWriter extends PrintWriter {
		public RecordingPrintWriter(PrintWriter writer) {
			super(writer);
		}

		@Override
		public void write(char[] buf, int off, int len) {
			super.write(buf, off, len);
			for (OutputStream recorder : outputRecorders) {
				PrintWriter recorderWriter = new PrintWriter(recorder);
				recorderWriter.write(buf, off, len);
				recorderWriter.flush();
			}
		}

		@Override
		public void write(int c) {
			super.write(c);
			for (OutputStream recorder : outputRecorders)
				try {
					recorder.write(c);
				} catch (IOException e) {
					throw new SailsException("Failure writing to an output recorder", e);
				}
		}

		@Override
		public void write(String s, int off, int len) {
			super.write(s, off, len);
			for (OutputStream recorder : outputRecorders) {
				PrintWriter recorderWriter = new PrintWriter(recorder);
				recorderWriter.write(s, off, len);
				recorderWriter.flush();
			}
		}
	}
}
