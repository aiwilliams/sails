package org.opensails.sails.tools;

import java.util.List;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.html.ActionLink;
import org.opensails.sails.html.ILink;
import org.opensails.sails.html.SimpleLink;
import org.opensails.sails.url.ExternalUrl;

public class LinkTool {
	protected final ISailsEvent event;

	public LinkTool(ISailsEvent event) {
		this.event = event;
	}

	/**
	 * @param action
	 * @return an ILink to an action of the controller for the event
	 */
	public ActionLink action(String action) {
		return new ActionLink(event).action(action);
	}

	public <T> ActionLink action(String action, List<T> parameters) {
		return action(action).parameters(parameters);
	}

	public ActionLink action(String action, Object... parameters) {
		return action(action).parameters(parameters);
	}

	/**
	 * @param controller
	 * @param action
	 * @return an ILink to an action of the specified controller
	 */
	public ActionLink action(String controller, String action) {
		return new ActionLink(event).controller(controller).action(action);
	}

	public ActionLink action(String controller, String action, List<? extends Object> parameters) {
		return action(controller, action).parameters(parameters);
	}

	public ActionLink action(String controller, String action, Object... parameters) {
		return action(controller, action).parameters(parameters);
	}

	public ILink controller() {
		return new ActionLink(event);
	}

	public ILink controller(String controller) {
		return new ActionLink(event).controller(controller);
	}

	public ILink href(String url) {
		return new SimpleLink<SimpleLink>(event, new ExternalUrl(event, url));
	}

	/**
	 * @return an ILink to the index action of the controller for the event
	 */
	public ILink index() {
		return new ActionLink(event);
	}
}
