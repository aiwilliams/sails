package org.opensails.sails;

public interface ISailsEventListener {
	void beginDispatch(ISailsEvent event);
	void endDispatch(ISailsEvent event);
}
