package org.opensails.sails.event.oem;

import org.opensails.sails.action.IActionResult;
import org.opensails.sails.event.ISailsEvent;

/**
 * Anything that can process an incoming ISailsEvent.
 * 
 * @author aiwilliams
 */
public interface IActionEventProcessor {
	public IActionResult process(ExceptionEvent event);

	public IActionResult process(GetEvent event);

	/**
	 * When there is no specific process method, as is the case when framework
	 * users create their own types of events, this is invoked.
	 * 
	 * @param event
	 * @return result of processing event
	 */
	public IActionResult process(ISailsEvent event);

	public IActionResult process(PostEvent event);
}
