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
import org.apache.commons.lang.ArrayUtils;
import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.ActionParameterList;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.event.ISailsEventListener;
import org.opensails.sails.form.FileUpload;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.url.EventUrl;
import org.opensails.sails.url.IEventUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;
import org.opensails.sails.util.ClassHelper;

public abstract class AbstractEvent implements ILifecycleEvent {
    protected ISailsApplication application;
    protected IEventContextContainer container;
    protected FormFields fields;
    protected ActionParameterList parameters;
    protected final HttpServletRequest req;
    protected final HttpServletResponse response;

    protected OutputStream responseOutputStream;
    protected PrintWriter responseWriter;
    protected EventUrl url;

    public AbstractEvent(ISailsApplication application, IScopedContainer parentContainer, HttpServletRequest req, HttpServletResponse resp) {
        this(req, resp);
        this.application = application;
        initialize(parentContainer);
    }

    // allow some control for subclasses
    protected AbstractEvent(HttpServletRequest req, HttpServletResponse resp) {
        this.req = new EventServletRequest(this, req);
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

    /**
     * @param prefix
     * @return all field names with prefix, minus the prefix itself
     */
    @SuppressWarnings("unchecked")
    protected String[] getFieldNamesPrefixed(String prefix) {
        if (fields.isEmpty()) return ArrayUtils.EMPTY_STRING_ARRAY;
        List<String> fieldNames = new ArrayList<String>(5);
        for (String key : fields.getNames())
            if (key.startsWith(prefix)) fieldNames.add(key.substring(prefix.length()));
        return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
    }

    protected void initialize(IScopedContainer parentContainer) {
        url = new EventUrl(req);
        container = createContainer(parentContainer);
        fields = createFormFields(req);
        container.register(FormFields.class, fields);
        containerSet();
    }
}
