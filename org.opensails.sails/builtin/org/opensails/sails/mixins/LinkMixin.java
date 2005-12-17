package org.opensails.sails.mixins;

import java.util.List;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.html.ActionLink;
import org.opensails.sails.html.ILink;

public class LinkMixin {
	protected final ISailsEvent event;

	public LinkMixin(ISailsEvent event) {
		this.event = event;
	}

	/**
	 * @param action
	 * @return an ILink to an action of the controller for the event
	 */
	public ActionLink action(String action) {
		return new ActionLink(event).action(action);
	}

	public ActionLink action(String action, List<? extends Object> parameters) {
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

	public ILink controller() {
		return new ActionLink(event);
	}

	public ILink controller(String controller) {
		return new ActionLink(event).controller(controller);
	}

	/**
	 * @return an ILink to the index action of the controller for the event
	 */
	public ILink index() {
		return new ActionLink(event);
	}
}
