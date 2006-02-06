package org.opensails.sails;

/**
 * <p>
 * Configures an {@link org.opensails.sails.ISailsApplication}.
 * </p>
 * 
 * <p>
 * Each instance of a Sails application must be provided certain services and
 * values to perform the work of responding to client requests. These services
 * include things like these (there are more):
 * <ul>
 * <li>Controller implementation resolution ({@link org.opensails.sails.controller.IControllerResolver})</li>
 * <li>Template, script, css, image etc. resolution ({@link org.opensails.sails.IResourceResolver})</li>
 * <li>Action result processor resolution ({@link org.opensails.sails.action.IActionResultProcessorResolver})</li>
 * <li>Parameter adapter resolution ({@link org.opensails.sails.adapter.IAdapterResolver})</li>
 * </ul>
 * </p>
 * 
 * @see org.opensails.sails.oem.BaseConfigurator
 * 
 * @author aiwilliams
 */
public interface ISailsApplicationConfigurator {
	/**
	 * Called when an ISailsApplication is started
	 * 
	 * @param application
	 */
	void configure(IConfigurableSailsApplication application);
}
