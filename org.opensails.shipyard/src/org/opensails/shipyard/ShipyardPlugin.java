package org.opensails.shipyard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.opensails.shipyard.model.Action;
import org.opensails.shipyard.model.Controller;
import org.opensails.shipyard.model.SailsModel;
import org.opensails.shipyard.model.SailsNature;
import org.opensails.shipyard.model.Template;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class ShipyardPlugin extends AbstractUIPlugin {
    public static final String PLUGIN_ID = "org.opensails.shipyard";

	//The shared instance.
	private static ShipyardPlugin plugin;

    protected static IWorkspace workspace;
    
    /**
	 * Returns the shared instance.
	 */
	public static ShipyardPlugin getDefault() {
		return plugin;
	}

	/**
	 * Return a <code>java.io.File</code> object that corresponds to the specified
	 * <code>IPath</code> in the plugin directory.
	 */
	public static File getFileInPlugin(IPath path) {
		try {
			URL installURL =
				new URL(getDefault().getBundle().getEntry("/"), path.toString());
			URL localURL = Platform.asLocalURL(installURL);
			return new File(localURL.getFile());
		} catch (IOException ioe) {
			return null;
		}
	}

    /**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
     * Returns the string from the plugin's resource bundle,
     * or 'key' if not found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = ShipyardPlugin.getDefault().getResourceBundle();
        try {
            return (bundle != null) ? bundle.getString(key) : key;
        } catch (MissingResourceException e) {
            return key;
        }
    }
    
    public static IWorkspace getWorkspace() {
        if (workspace == null)
            workspace = ResourcesPlugin.getWorkspace();
        return workspace;
    }

	public static void setWorkspace(IWorkspace workspace) {
        ShipyardPlugin.workspace = workspace;
    }

    //Resource bundle.
    private ResourceBundle resourceBundle;

	protected SailsModel sailsModel;

	/**
	 * The constructor.
	 */
	public ShipyardPlugin() {
		plugin = this;
	}

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        try {
            if (resourceBundle == null)
                resourceBundle = ResourceBundle.getBundle("org.opensails.shipyard.ShipyardPluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
        return resourceBundle;
    }

    public SailsModel getSailsModel() {
        if (sailsModel == null)
            sailsModel = new SailsModel();
        return sailsModel;
    }
    
    /**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
        setupImageRegistry();
    }

    /**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
        resourceBundle = null;
	}

	protected void setupImageRegistry() {
        getImageRegistry().put(SailsNature.class.getName(), getImageDescriptor("icons/nature.gif"));
        getImageRegistry().put(Controller.class.getName(), getImageDescriptor("icons/controller.gif"));
        getImageRegistry().put(Template.class.getName(), getImageDescriptor("icons/template.gif"));
        getImageRegistry().put(Action.class.getName(), getImageDescriptor("icons/action.gif"));
    }
}
