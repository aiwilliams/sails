package org.opensails.sails.helper.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.html.ActionLink;
import org.opensails.sails.html.ILink;

public class LinkHelper {
	protected final ISailsEvent event;

	public LinkHelper(ISailsEvent event) {
		this.event = event;
	}

	public ILink index() {
		return new ActionLink(event);
	}
}
