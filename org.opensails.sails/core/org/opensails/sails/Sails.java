package org.opensails.sails;

import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.spyglass.SpyGlass;

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
	public static final String DEFAULT_CONTEXT_ROOT_DIRECTORY = "app";

	/**
	 * Converts a Component implementation class into a String that could be the
	 * Component's name.
	 */
	public static final String componentName(Class<? extends IComponentImpl> componentClass) {
		return SpyGlass.lowerCamelName(componentClass).replaceFirst("Component$", "");
	}

	/**
	 * Converts a Controller implementation class into a String that could be
	 * the Controller's name.
	 * 
	 * @see #controllerName(Controller) to avoid getClass() calls
	 */
	public static final String controllerName(Class<? extends IControllerImpl> controllerClass) {
		return SpyGlass.lowerCamelName(controllerClass).replaceFirst("Controller$", "");
	}

	/**
	 * A convenient use of {@link #controllerName(Class)}.
	 * 
	 * @see #controllerName(Class)
	 */
	public static final String controllerName(IControllerImpl instance) {
		return controllerName(instance.getClass());
	}

	@SuppressWarnings("unchecked")
	public static String eventContextName(Class<? extends IEventProcessingContext> processor) {
		if (IControllerImpl.class.isAssignableFrom(processor)) return controllerName((Class<? extends IControllerImpl>) processor);
		if (IComponentImpl.class.isAssignableFrom(processor)) return "component_" + componentName((Class<? extends IComponentImpl>) processor);
		throw new SailsException("I have no idea what you are asking me to do");
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

	public interface QueryParam {
		String ORIGIN = "origin";
	}
}
