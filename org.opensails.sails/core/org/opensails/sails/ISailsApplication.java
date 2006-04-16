package org.opensails.sails;

import org.apache.commons.configuration.Configuration;

/**
 * The 'runtime' interface of a Sails application. This is what clients see the
 * application as normally, after it has been initialized. Every
 * {@link org.opensails.sails.event.ISailsEvent} belongs to one of these.
 * 
 * @author aiwilliams
 * 
 */
public interface ISailsApplication {
	Configuration getConfiguration();

	ApplicationContainer getContainer();

	String getName();

	/**
	 * @return the package this application lives in
	 */
	String getPackageName();

	/**
	 * @param <T>
	 * @param key
	 * @return the object registered in the ApplicationContainer for key, null
	 *         if nothing
	 */
	<T> T instance(Class<T> key);

	/**
	 * @param <T>
	 * @param key
	 * @param defaultImplementation
	 * @return the object registered in the ApplicationContainer for key, an
	 *         instance of defaultImplementation if none
	 */
	<T> T instance(Class<T> key, Class defaultImplementation);

}
