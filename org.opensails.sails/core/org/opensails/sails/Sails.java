/*
 * Created on Mar 2, 2005
 * 
 * (c) 2005 Adam Williams
 */

package org.opensails.sails;

import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.util.ClassHelper;

/*
 * Don't let this become a dumping ground. Methods should be domain specific.
 * What is our domain? Sails!
 */
/**
 * A collection of functions that simplify coding and encapsulate common
 * behaviour.
 */
public class Sails {
	/**
	 * The default directory name for a Sails Application's context root.
	 */
	public static final String DEFAULT_CONTEXT_ROOT_DIRECTORY = "war";

	/**
	 * Converts a Controller implementation class into a String that could be
	 * the Controller's name.
	 * 
	 * @see #controllerName(Controller) to avoid getClass() calls
	 */
	public static final String controllerName(Class controllerClass) {
		return ClassHelper.lowerCamelName(controllerClass).replaceFirst("Controller$", "");
	}

	/**
	 * A convenient use of {@link #controllerName(Class)}.
	 * 
	 * @see #controllerName(Class)
	 */
	public static final String controllerName(Controller instance) {
		return controllerName(instance.getClass());
	}

	/**
	 * Changes AWordLikeThis to A Word Like This. Careful, this trims.
	 * 
	 * @param wordsInCamelCase
	 * @return camel made spaces
	 */
	public static String spaceCamelWords(String wordsInCamelCase) {
		return wordsInCamelCase.replaceAll("([A-Z])", " $0").trim();
	}

	public interface ConfigurationKey {
		public interface Url {
			String IMAGES = "sails.url.images.pathextension";
			String SCRIPTS = "sails.url.scripts.pathextension";
			String SECURE_SCHEME = "sails.url.secure.scheme";
			String STYLES = "sails.url.styles.pathextension";
		}
	}
}
