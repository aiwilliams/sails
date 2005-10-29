/*
 * Created on May 6, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.shipyard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.opensails.sails.SailsPlugin;

/**
 * Wraps up knowledge about how to contribute IClasspathEntry's to a
 * JavaProject.
 * 
 * Right now, it is necessary for a workspace to include the org.opensails.sails
 * project. The goal is to get to a point where the compiled product and source
 * ship with the plugin.
 */
public class SailsClasspathContainer implements IClasspathContainer {
    public static final String ID = "org.opensails.shipyard.classpath.container.initializer.sails";

    public IClasspathEntry[] getClasspathEntries() {
        IProject project = ShipyardPlugin.getWorkspace().getRoot().getProject("org.opensails.sails");
        if (project.exists()) return new IClasspathEntry[] { JavaCore.newProjectEntry(project.getFullPath()) };

        try {
            IPath sailsPluginRoot = SailsPlugin.getRootPath();
            JavaCore.setClasspathVariable("SAILS_LIB", sailsPluginRoot.makeAbsolute(), null);
        } catch (JavaModelException e) {
            throw new RuntimeException("Couldn't find this plugin");
        }
        IPath sailsLib = JavaCore.getClasspathVariable("SAILS_LIB");
        return new IClasspathEntry[] {
            JavaCore.newLibraryEntry(sailsLib.append(new Path("sails.jar")), null, null),
            JavaCore.newLibraryEntry(sailsLib.append(new Path("vendor/jakarta/commons/lib/commons-codec.jar")), null, null),
            JavaCore.newLibraryEntry(sailsLib.append(new Path("vendor/jakarta/commons/lib/commons-collections.jar")), null, null),
            JavaCore.newLibraryEntry(sailsLib.append(new Path("vendor/jakarta/commons/lib/commons-configuration.jar")), null, null),
            JavaCore.newLibraryEntry(sailsLib.append(new Path("vendor/jakarta/commons/lib/commons-lang.jar")), null, null),
            JavaCore.newLibraryEntry(sailsLib.append(new Path("vendor/jakarta/commons/lib/commons-logging.jar")), null, null),
            JavaCore.newLibraryEntry(sailsLib.append(new Path("vendor/jetty/lib/javax.servlet.jar")), null, null),
            JavaCore.newLibraryEntry(sailsLib.append(new Path("vendor/jetty/lib/org.mortbay.jetty.jar")), null, null),
            JavaCore.newLibraryEntry(sailsLib.append(new Path("vendor/jetty/lib/org.mortbay.jmx.jar")), null, null),
        };
    }

    public String getDescription() {
        return "The Sails Library";
    }

    public int getKind() {
        return K_APPLICATION;
    }

    public IPath getPath() {
        return new Path(ID);
    }
}
