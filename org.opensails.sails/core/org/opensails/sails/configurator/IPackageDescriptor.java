package org.opensails.sails.configurator;

import java.util.List;

import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.controller.IControllerImpl;

/**
 * Provides a SailsApplication with the ApplicationPackages it should consult
 * when looking for various classes.
 * 
 * @author aiwilliams
 */
public interface IPackageDescriptor {
	/**
	 * @return a List of locations where a Sails application should look for
	 *         {@link IAdapter}s
	 */
	List<ApplicationPackage> getAdapterPackages();

	/**
	 * @return the root package of a Sails application
	 */
	ApplicationPackage getApplicationPackage();

	/**
	 * @return a List of locations where a Sails application should look for
	 *         {@link IComponentImpl}s
	 */
	List<ApplicationPackage> getComponentPackages();

	/**
	 * @return a List of locations where a Sails application should look for
	 *         {@link IControllerImpl}s
	 */
	List<ApplicationPackage> getControllerPackages();

	/**
	 * @return a List of locations where a Sails application should look for
	 *         template mixins
	 */
	List<ApplicationPackage> getMixinPackages();

	/**
	 * @return a List of locations where a Sails application should look for
	 *         {@link IActionResultProcessor}s
	 */
	List<ApplicationPackage> getResultProcessorPackages();

	/**
	 * TODO Use this - It isn't being used yet.
	 * 
	 * @return a List of locations where a Sails application should look for
	 *         template tools
	 */
	List<ApplicationPackage> getToolPackages();
}
