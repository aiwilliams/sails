package org.opensails.sails.event;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.action.IActionParameterList;
import org.opensails.sails.form.FileUpload;
import org.opensails.sails.url.IEventUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlType;

/**
 * Every interaction with a Sails application is triggered as an ISailsEvent.
 * 
 * This is the interface as seen once an event has been dispatched.
 */
public interface ISailsEvent {
	/**
	 * @return the name of the action this event is bound for
	 */
	String getActionName();

	/**
	 * @return the parameters in the URL
	 */
	IActionParameterList getActionParameters();

	/**
	 * @return the application that generated this event
	 */
	ISailsApplication getApplication();

	Configuration getConfiguration();

	/**
	 * @return the event container (request scoped)
	 */
	RequestContainer getContainer();

	IEventUrl getEventUrl();

	/**
	 * The value of a named field in a request (a request parameter). The term
	 * 'field' was chosen, as parameter causes some confusion due to the fact
	 * that a Sails url is composed of a controller name, action name, then
	 * parameters. For a GET, this fields are the ?name=value&amp; (url query)
	 * things. For a POST, they come from both the url query and the body of the
	 * request.
	 * 
	 * @see #getFieldValues(String)
	 * @see HttpServletRequest#getParameter(java.lang.String)
	 * @param name
	 * @return the value for the request parameter with name
	 */
	String getFieldValue(String name);

	/**
	 * @see #getFieldValue(String)
	 * @see HttpServletRequest#getParameterValues(java.lang.String)
	 * @param name
	 * @return the values for the request parameter with name
	 */
	String[] getFieldValues(String name);

	/**
	 * @param name
	 * @return the FileUpload for the given name, null if it's not there
	 */
	FileUpload getFileUpload(String name);

	/**
	 * @return the name of the IActionEventProcessor this event is bound for
	 */
	String getProcessorName();

	HttpServletRequest getRequest();

	HttpServletResponse getResponse();

	/**
	 * @return the OutputStream of the response
	 * @throws IllegalStateException if this gets called AFTER
	 *         #getResponseWriter()
	 */
	OutputStream getResponseOutputStream();

	/**
	 * @return a PrintWriter from the response
	 * @throws IllegalStateException if an output stream was obtained first
	 */
	PrintWriter getResponseWriter();

	HttpSession getSession(boolean create);

	/**
	 * To provide for url path extension in such a way as to not limit the
	 * possibilities, this provides a hook into the {@link IUrlResolver}s.
	 * 
	 * @see IUrlResolver
	 * @param pathType the PathType to extend urlFragment as
	 * @param urlFragment the url to extend
	 * @return an extended url
	 */
	IUrl resolve(UrlType pathType, String urlFragment);

	/**
	 * Appends content to the body of the response to the client. This is going
	 * to the reponse buffer, and may be cleared if the response has not been
	 * committed (data sent to the client).
	 * 
	 * @param text
	 */
	void write(String text);
}
