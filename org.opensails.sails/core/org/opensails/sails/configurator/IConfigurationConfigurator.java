package org.opensails.sails.configurator;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.configurator.oem.DefaultConfigurationConfigurator;

/**
 * Configures the application's Configuration.
 * <p>
 * The Configuration allows us to configure a Sails application outside of code.
 * There is only one instance per Sails application. You can manipulate this
 * here, or through one of the following mechanisms:
 * <ul>
 * <li>web.xml - add something like this to the Servlet declaration for the
 * application:
 * 
 * <pre>
 *     &lt;init-param&gt;
 *      &lt;param-name&gt;org.opensails.sails.ISailsApplicationConfigurator&lt;/param-name&gt;
 *      &lt;param-value&gt;afi.Configurator&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 * </pre>
 * 
 * </li>
 * <li>-D arguments to the jvm</li>
 * <li>System.setProperty()</li>
 * </ul>
 * 
 * @author aiwilliams
 * @see DefaultConfigurationConfigurator
 */
public interface IConfigurationConfigurator {
	void configure(IConfigurableSailsApplication application, CompositeConfiguration compositeConfiguration);
}
