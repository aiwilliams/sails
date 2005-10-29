/*
 * Created on May 6, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.shipyard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * Initializes the classpath container for the Sails library.
 */
public class SailsClasspathContainerInitializer extends org.eclipse.jdt.core.ClasspathContainerInitializer {
    public void initialize(IPath containerPath, IJavaProject project) throws CoreException {
        SailsClasspathContainer container = new SailsClasspathContainer();
        JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project }, new IClasspathContainer[] { container }, null);
    }
}