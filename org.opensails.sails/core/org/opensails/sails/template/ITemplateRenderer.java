package org.opensails.sails.template;

import org.opensails.sails.url.IUrl;
import org.opensails.viento.IBinding;

/**
 * Allows for the rendering of templates from anywhere within the
 * SailsApplication.
 * 
 * @author aiwilliams
 */
public interface ITemplateRenderer<T extends IBinding> {
	/**
	 * @param parent
	 * @return a new T with given parent
	 */
	T createBinding(T parent);

	/**
	 * @param templateUrl
	 * @param binding
	 * @return result of render
	 */
	StringBuilder render(IUrl templateUrl, T binding);

	/**
	 * @param templateIdentifier
	 * @param binding
	 * @return the result of rendering the template specified by
	 *         templateIdentifier, using the provided binding
	 */
	StringBuilder render(String templateIdentifier, T binding);

	/**
	 * @param templateIdentifier
	 * @param binding
	 * @param target to write to
	 * @return the target
	 */
	StringBuilder render(String templateIdentifier, T binding, StringBuilder target);

	/**
	 * @param templateContent
	 * @param binding
	 * @return result of render
	 */
	StringBuilder renderString(String templateContent, T binding);

	/**
	 * @param templateIdentifier
	 * @return true if the template specified by templateIdentifier exists
	 */
	boolean templateExists(String templateIdentifier);
}
