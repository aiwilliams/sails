package org.opensails.sails.helper.oem;

import java.util.List;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.html.ActionLink;
import org.opensails.sails.html.ILink;
import org.opensails.sails.url.ActionUrl;

public class LinkMixin {
	protected final ISailsEvent event;

	public LinkMixin(ISailsEvent event) {
		this.event = event;
	}

	/**
	 * @param action
	 * @return an ILink to an action of the controller for the event
	 */
	public ILink action(String action) {
		throw new RuntimeException("implement");
	}

	public ILink action(String action, List<?> parameters) {
		throw new RuntimeException("implement");
	}

	/**
	 * @param controller
	 * @param action
	 * @return an ILink to an action of the specified controller
	 */
	public ILink action(String controller, String action) {
		throw new RuntimeException("implement");
	}

	public ActionUrl action(String controller, String action, List<?> parameters) {
		throw new RuntimeException("implement");
	}

	public ILink controller() {
		throw new RuntimeException("implement");
	}

	public ILink controller(String controller) {
		throw new RuntimeException("implement");
	}

	/**
	 * @return an ILink to the index action of the controller for the event
	 */
	public ILink index() {
		return new ActionLink(event);
	}
}
