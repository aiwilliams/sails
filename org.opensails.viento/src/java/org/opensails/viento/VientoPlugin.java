package org.opensails.viento;

import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class VientoPlugin extends Plugin {
    public static final String PLUGIN_ID = "org.opensails.viento";

    // The shared instance.
    private static VientoPlugin plugin;

    /**
     * Returns the shared instance.
     */
    public static VientoPlugin getDefault() {
        return plugin;
    }

    public static IPath getRootPath() {
        try {
            URL installURL = getDefault().getBundle().getEntry("/");
            URL localURL = Platform.asLocalURL(installURL);
            return new Path(localURL.getPath());
        } catch (IOException ioe) {
            return null;
        }
    }

    // Resource bundle.
    private ResourceBundle resourceBundle;

    public VientoPlugin() {
        super();
        plugin = this;
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        try {
            if (resourceBundle == null) resourceBundle = ResourceBundle.getBundle("org.opensails.viento.VientoPluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
        return resourceBundle;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
        resourceBundle = null;
    }
}
