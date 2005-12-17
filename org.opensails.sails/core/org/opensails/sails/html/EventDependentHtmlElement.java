package org.opensails.sails.html;

import org.opensails.sails.event.ISailsEvent;

public class EventDependentHtmlElement<T extends EventDependentHtmlElement> extends AbstractHtmlElement<T> {
	protected ISailsEvent event;

	public EventDependentHtmlElement(String elementName, ISailsEvent event) {
		super(elementName);
		this.event = event;
	}
}
