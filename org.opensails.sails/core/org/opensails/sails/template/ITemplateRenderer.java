package org.opensails.sails.template;

/**
 * Allows for the rendering of templates from anywhere within the
 * SailsApplication.
 * 
 * @author aiwilliams
 */
public interface ITemplateRenderer<T extends ITemplateBinding> {
	/**
	 * @param parent
	 * @return a new T with given parent
	 */
	T createBinding(T parent);

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
	 * @param templateIdentifier
	 * @return true if the template specified by templateIdentifier exists
	 */
	boolean templateExists(String templateIdentifier);
}
